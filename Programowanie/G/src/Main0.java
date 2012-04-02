import javax.management.RuntimeOperationsException;
import java.io.IOException;

/**
 * User: Maciej Poleski
 * Date: 23.03.12
 * Time: 21:56
 */
class Cat {
    public void aaaa() throws IOException {
        System.out.println("Miau!");
    }

    public void miau() {
        System.out.println("Miau!");
    }

    private String hello() throws IndexOutOfBoundsException {
        return "Hello, miau.";
    }

    Cat() throws RuntimeOperationsException, IOException, IndexOutOfBoundsException {
    }
}

public class Main0 {
    public String hello() throws RuntimeOperationsException, IndexOutOfBoundsException {
        return "Hello";
    }

    private String hello(int name) {
        return "Hello " + name;
    }

    public static void main(String args[]) throws RuntimeOperationsException, IndexOutOfBoundsException, IOException {
        ExceptionCounter counter = new ExceptionCounter();
        Cat c = new Cat();
        Main0 m = new Main0();
        counter.register(c);
        counter.register(m);
        counter.printStatus();
        counter.register(new Main0());
        c = null;
        System.gc();
        counter.printStatus();
        System.out.println(m.hello());
    }
}