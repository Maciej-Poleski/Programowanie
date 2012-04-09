import java.io.IOException;
import java.io.PipedReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: Maciej Poleski
 * Date: 06.04.12
 * Time: 21:35
 */
public class MessageListener {
    private final int maxMessages;
    private final Map<Long, String> readyMessages = new TreeMap<Long, String>();
    private final ReentrantLock readyMessagesLock = new ReentrantLock();
    private final AtomicBoolean acceptingNewMessages = new AtomicBoolean(true);
    private final Map<PipedReader, Listener> listeners = Collections.synchronizedMap(new HashMap<PipedReader, Listener>());
    private final Thread mainThread;
    private final Thread daemonKeeperThread;
    private final ReadWriteLock taskLock = new ReentrantReadWriteLock();

    public MessageListener(int maxMessages) {
        this.maxMessages = maxMessages;
        mainThread = Thread.currentThread();
        daemonKeeperThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    // We are ready - waiting is not reasonable
                }
                try {
                    taskLock.writeLock().lock();
                    acceptingNewMessages.set(false);
                    printAnswer();
                } finally {
                    taskLock.writeLock().unlock();
                }
            }
        });
        daemonKeeperThread.setDaemon(false);
        daemonKeeperThread.start();
    }

    class Listener implements Runnable {
        long ID;
        String message;
        final AtomicBoolean stop = new AtomicBoolean(false);
        final PipedReader reader;

        Listener(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                while (!stop.get()) {
                    int length = reader.read() + reader.read() * 256;
                    char[] result = new char[length];
                    for (int i = 0; i < length; ++i)
                        result[i] = (char) reader.read();
                    message = new StringBuilder().append(result).reverse().toString();
                    ID = reader.read() + (reader.read() << 8) + (reader.read() << 16) + (reader.read() << 24);
                    messageIsReady();
                }
            } catch (IOException ignored) {
            }
        }

        void messageIsReady() {
            try {
                taskLock.readLock().lock();
                if (acceptingNewMessages.get()) {
                    try {
                        readyMessagesLock.lock();
                        if (!acceptingNewMessages.get())
                            return;
                        if (readyMessages.containsKey(ID)) {
                            String oldString = readyMessages.get(ID);
                            if (message.compareTo(oldString) > 0)
                                readyMessages.put(ID, message);
                        } else {
                            readyMessages.put(ID, message);
                        }
                        if (readyMessages.size() == maxMessages) {
                            acceptingNewMessages.set(false);
                            daemonKeeperThread.interrupt();
                        }
                    } finally {
                        readyMessagesLock.unlock();
                    }
                }
            } finally {
                taskLock.readLock().unlock();
            }
        }
    }

    private void printAnswer() {
        try {
            readyMessagesLock.lock();
            if (readyMessages.size() != maxMessages)
                return;
            for (String string : readyMessages.values())
                System.out.println(string);
        } finally {
            readyMessagesLock.unlock();
        }
    }

    public void listen(PipedReader pr) {
        Listener listener = new Listener(pr);
        listeners.put(pr, listener);
        Thread thread = new Thread(listener);
        thread.setDaemon(true);
        thread.start();
    }

    public void drop(PipedReader pr) {
        listeners.get(pr).stop.set(true);
        listeners.remove(pr);
    }
}