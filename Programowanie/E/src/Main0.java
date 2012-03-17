import java.util.LinkedList;

/**
 * User: Maciej Poleski
 * Date: 15.03.12
 * Time: 17:33
 */
public class Main0 {
    static class Even implements FreakList<Integer> {
        private LinkedList<Integer> ttt = new LinkedList<Integer>();
        {
            for (int i = 0; i < 5; i++)
                ttt.add(2 * i);
        }
        @Override
        public Integer pop() throws AccessDenied, WasWrong, IsEmpty {
            if (ttt.isEmpty())
                throw new IsEmpty();
            return ttt.pop();
        }
    }
    static class Odd implements FreakList<Integer> {
        private LinkedList<Integer> ttt = new LinkedList<Integer>();
        {
            for (int i = 0; i < 5; i++)
                ttt.add(2 * i + 1);
        }
        @Override
        public Integer pop() throws AccessDenied, WasWrong, IsEmpty {
            if (ttt.isEmpty())
                throw new IsEmpty();
            return ttt.pop();
        }
    }
    public static void main(String arg[]) {
        Freak.merge(new Even(), new Odd());
    }
}