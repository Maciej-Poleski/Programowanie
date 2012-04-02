import java.io.IOError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Maciej Poleski
 * Date: 23.03.12
 * Time: 22:03
 */
class MyError extends IOError {
    public MyError(Throwable cause) {
        super(cause);
    }
}
class Box<T> extends HashMap<T,T> {
    public Box() throws IOError {}
    T get() throws MyError {return null;}
    void set(T x) throws IOError {}
    @Override
    public Set<Map.Entry<T, T>> entrySet() throws MyError , IOError{
        return super.entrySet();
    }
    List<? extends Comparable<T>>[][] set() throws MyError {return null;}
}
public class Main1 {
    public static void main(String... args){
        ExceptionCounter counter = new ExceptionCounter();
        Box<Integer> x = new Box<Integer>();
        counter.register(x);
        counter.register(x);
        counter.printStatus();
        System.out.println(x);
    }
}


