/**
 * User: Maciej Poleski
 * Date: 07.04.12
 * Time: 15:00
 */
import java.util.concurrent.TimeUnit;
public class Test2 {

    public static class SillyThread extends Thread {

        Locker.Lock one;
        String me;
        int delay;

        public SillyThread(String me, int delay, Locker.Lock one) {
            this.me = me;
            this.delay = delay;
            this.one = one;
        }

        protected void log(String msg) {
            System.out.println("<" + me + ">: " + msg);
        }
        @Override
        public void run() {
            try {
                one.lock();
                log("got first lock");
                try {
                    TimeUnit.SECONDS.sleep(delay);
                    log("about to wait on second lock");
                    one.lock();
                    log("got second lock");
                    one.unlock();
                    log("unlocked second, time to sleep");
                    TimeUnit.SECONDS.sleep(delay);
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
        Locker.Lock lock = locker.getLock();
        new SillyThread("ONE", 1, lock).start();
        TimeUnit.MILLISECONDS.sleep(10);
        new SillyThread("TWO", 1, lock).start();
    }
}