/**
 * User: Maciej Poleski
 * Date: 07.04.12
 * Time: 15:00
 */
import java.util.concurrent.TimeUnit;

public class Test1 {

    public static class SillyThread extends Thread  {
        Locker.Lock one, two;
        String me;
        int delay;

        public SillyThread(String me, int delay, Locker.Lock one, Locker.Lock two) {
            this.me = me;
            this.delay = delay;
            this.one = one;
            this.two = two;
        }

        protected void log(String msg) {
            System.out.println("<" + me + ">: " + msg);
        }
        @Override
        public void run() {
            try {
                one.lock();
                TimeUnit.MILLISECONDS.sleep(10);
                log("got first lock");
                try {
                    TimeUnit.SECONDS.sleep(delay);
                    log("about to wait on second lock");
                    two.lock();
                    log("got second lock");
                    two.unlock();
                } finally {
                    log("releasing first lock");
                    one.unlock();
                }
            } catch (Locker.DeadlockException e) {
                log("DeadlockException was thrown.");
            } catch (Exception s){
                log("Other exception");
            }
            log("finishing smoothly");
        }
    }

    public static void main(String[] args) throws Locker.DeadlockException, InterruptedException {
        Locker locker = new Locker();
        Locker.Lock lock1 = locker.getLock(), lock2 = locker.getLock();
        new SillyThread("ONE", 1, lock1, lock2).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new SillyThread("TWO", 2, lock1, lock2).start();
    }
}