import javax.management.RuntimeMBeanException;
import java.util.Map;

/**
 * User: Maciej Poleski
 * Date: 23.03.12
 * Time: 22:05
 */
public class Main2 {
    void test(Map.Entry newTable) throws RuntimeMBeanException {
    }

    Map.Entry<Integer, Integer> test2() throws RuntimeMBeanException {
        return null;
    }

    Map.Entry[] test3() throws RuntimeMBeanException {
        return null;
    }

    public String toString() throws RuntimeMBeanException {
        return "Type test";
    }

    public static void main(String args[]) {
        ExceptionCounter counter = new ExceptionCounter();
        Main2 o = new Main2();
        counter.register(o);
        counter.printStatus();
        System.out.println(o);
    }
}