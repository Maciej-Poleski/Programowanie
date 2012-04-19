import java.io.IOException;
import java.io.PipedReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: Maciej Poleski
 * Date: 06.04.12
 * Time: 21:35
 */
public class MessageListener {
    private final SortedMap<Long, String> messages = new TreeMap<Long, String>();
    private final Map<PipedReader, Listener> listenerMap = new HashMap<PipedReader, Listener>();
    private final Observer observer;
    private final int maxMessages;

    public MessageListener(int maxMessages) {
        this.maxMessages = maxMessages;
        this.observer = new Observer(Thread.currentThread());
        this.observer.start();
    }

    public void listen(PipedReader pr) {
        synchronized (this) {
            if (listenerMap.containsKey(pr))
                return;
            Listener listener = new Listener(pr);
            listenerMap.put(pr, listener);
            listener.start();
        }
    }

    public void drop(PipedReader pr) {
        synchronized (this) {
            if (!listenerMap.containsKey(pr))
                return;
            listenerMap.get(pr).drop();
            listenerMap.remove(pr);
        }
    }

    private class Listener extends Thread {
        private final PipedReader reader;
        private final AtomicBoolean stop = new AtomicBoolean(false);

        Listener(PipedReader reader) {
            this.reader = reader;
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (stop.get()) {
                        break;
                    }
                    int length = reader.read();
                    if (length == -1) {
                        break;
                    }
                    length += (reader.read() << 8);
                    char[] buffer = new char[length];
                    for (int i = 0; i < length; ++i) {
                        buffer[i] = (char) reader.read();
                    }
                    String message = new StringBuilder().append(buffer).reverse().toString();
                    long id = reader.read() + (reader.read() << 8) + (reader.read() << 16) + (reader.read() << 24);
                    addMessage(message, id);
                }
            } catch (IOException ignored) {
            }
        }

        public void drop() {
            stop.set(true);
        }
    }

    private class Observer extends Thread {
        final Thread mainThread;

        private Observer(Thread mainThread) {
            this.mainThread = mainThread;
            setDaemon(false);
        }

        @Override
        public void run() {
            try {
                mainThread.join();
            } catch (InterruptedException ignored) {
            }
            List<Thread> join = new ArrayList<Thread>();
            synchronized (MessageListener.this) {
                join.addAll(listenerMap.values());
            }
            for (Thread thread : join) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // false
                }
            }
            synchronized (MessageListener.this) {
                if (messages.size() == maxMessages) {
                    for (String message : messages.values()) {
                        System.out.println(message);
                    }
                }
            }
        }
    }

    private synchronized void addMessage(String message, long id) {
        if (messages.size() < maxMessages) {
            if (messages.containsKey(id)) {
                if (messages.get(id).compareTo(message) < 0) {
                    messages.put(id, message);
                }
            } else {
                messages.put(id, message);
            }
        } else {
            observer.interrupt();
        }
    }
}