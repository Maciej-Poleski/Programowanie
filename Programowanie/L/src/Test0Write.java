import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * User: Maciej Poleski
 * Date: 14.04.12
 * Time: 19:26
 */
@SuppressWarnings("serial")
public class Test0Write implements Runnable, Serializable {
    @Override
    public void run(){
        System.out.println("Hello there");
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        OnceMore.produceSaveAndRun(new FileOutputStream("test.dat"), "Test0Write");
    }
}