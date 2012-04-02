import java.util.List;

/**
 * User: Maciej Poleski
 * Date: 23.03.12
 * Time: 16:15
 */
public class Examples {
    static class Example1 {
        public final <T> List<Integer> getListOfInteger(Comparable<T> arg1, Iterable<?> arg2) throws IndexOutOfBoundsException {
            return null;
        }
    }

    public static void main(String... args) {
        ExceptionCounter counter = new ExceptionCounter();
        counter.register(new Example1());
        counter.printStatus();
    }
}
