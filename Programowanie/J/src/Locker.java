import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Maciej Poleski
 * Date: 07.04.12
 * Time: 14:53
 */
public class Locker {
    private final Map<Lock, Thread> lockedLocks = new HashMap<Lock, Thread>();
    private final Map<Thread, Thread> dependencies = new HashMap<Thread, Thread>();

    public Lock getLock() {
        return new Lock();
    }

    public class Lock {
        private final ReentrantLock realLock = new ReentrantLock();

        Lock() {
        }

        public void lock() {
            synchronized (Locker.this) {
                if (realLock.isHeldByCurrentThread()) {
                    realLock.lock();
                    return;
                } else {
                    if (lockedLocks.containsKey(this)) {
                        new CycleChecker(Thread.currentThread()).checkCycle(lockedLocks.get(this));
                    }
                    dependencies.put(Thread.currentThread(), lockedLocks.get(this));
                }
            }
            realLock.lock();
            synchronized (Locker.this) {
                lockedLocks.put(this, Thread.currentThread());
                dependencies.remove(Thread.currentThread());
            }
        }

        public void unlock() {
            synchronized (Locker.this) {
                int locks = realLock.getHoldCount();
                realLock.unlock();
                if (locks == 1) {
                    lockedLocks.remove(this);
                }
            }
        }
    }

    private class CycleChecker {
        final Thread candidate;

        public CycleChecker(Thread candidate) {
            this.candidate = candidate;
        }

        public void checkCycle(Thread thread) {
            if (thread == null)
                return;
            if (thread == candidate)
                throw new DeadlockException();
            else if (dependencies.containsKey(thread)) {
                checkCycle(dependencies.get(thread));
            }
        }
    }

    public static class DeadlockException extends RuntimeException {
        public DeadlockException() {
        }

        public DeadlockException(String message) {
            super(message);
        }

        public DeadlockException(String message, Throwable cause) {
            super(message, cause);
        }

        public DeadlockException(Throwable cause) {
            super(cause);
        }
    }
}
