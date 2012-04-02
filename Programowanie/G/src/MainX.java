import java.util.ArrayList;
import java.util.LinkedList;

/**
 * User: Maciej Poleski
 * Date: 27.03.12
 * Time: 21:13
 */
public class MainX {
    public static void main(String args[]) {
        ExceptionCounter counter = new ExceptionCounter();
        Object X = new ArrayList();
        Object Y = new LinkedList();
        counter.register(X);
        counter.printStatus();
        counter.register(Y);
        counter.printStatus();
        System.out.println(X);
        System.out.println(Y);
    }
}