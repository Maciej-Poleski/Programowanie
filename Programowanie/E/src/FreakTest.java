import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * User: Maciej Poleski
 * Date: 17.03.12
 * Time: 14:53
 */
public class FreakTest {
    static class InputProvider<T extends Comparable<T>> implements FreakList<T> {
        boolean wasWrong = false;
        boolean nowMustBeOk=false;
        List<T> queue;
        Random random = new Random();

        InputProvider(List<T> queue) {
            this.queue = new LinkedList<T>(queue);
        }

        @Override
        public T pop() throws AccessDenied, WasWrong, IsEmpty {
            if (wasWrong) {
                wasWrong = false;
                nowMustBeOk=true;
                throw new WasWrong();
            }
            if (queue.isEmpty())
                throw new IsEmpty();
            int branch = random.nextInt(4);
            if (!nowMustBeOk && branch == 0) {
                wasWrong = true;
                throw new IsEmpty();
            } else if (!nowMustBeOk && branch == 1) {
                throw new AccessDenied();
            } else if (!nowMustBeOk && branch == 2 && queue.size() > 1) {
                T result = queue.get(1);
                queue.remove(1);
                wasWrong = true;
                return result;
            } else if (nowMustBeOk || branch == 3 || queue.size() == 1) {
                T result = queue.get(0);
                queue.remove(0);
                nowMustBeOk=false;
                return result;
            } else {
                throw new IllegalStateException("Zaszedł nieobsługiwany stan");
            }
        }
    }

    @Test
    public void testMerge() throws Exception {
        List<Integer> pool = new LinkedList<Integer>();
        final int size = 100000;
        for (int i = 0; i < size; ++i)
            pool.add(i);
        InputProvider<Integer> input = new InputProvider<Integer>(pool);
        FreakListBypassWasWrong<Integer> list = new FreakListBypassWasWrong<Integer>(new FreakListBypassAccessDenied<Integer>(input));
        for (Integer i : pool) {
            try {
                assertEquals(i, list.pop());
            } catch (IsEmpty e) {
                fail("Przedwczesny koniec", e);
            }
        }
        for (int i = 0; i < 50; ++i) {
            try {
                list.pop();
                fail("Powinien wystąpić wyjątek IsEmpty");
            } catch (IsEmpty e) {
                // Wszystko OK
            }
        }
        List<Integer> input1=new LinkedList<Integer>();
        List<Integer> input2=new LinkedList<Integer>();
        Random rand=new Random();
        for(Integer i: pool)
        {
            if(rand.nextBoolean())
                input1.add(i);
            else
                input2.add(i);
        }
        Freak.merge(new InputProvider<Integer>(input1),new InputProvider<Integer>(input2));
    }
}
