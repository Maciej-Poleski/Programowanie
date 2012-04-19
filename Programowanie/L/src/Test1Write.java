import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * User: Maciej Poleski
 * Date: 14.04.12
 * Time: 19:27
 */
@SuppressWarnings("serial")
public class Test1Write implements Runnable, Serializable {

    class T1 implements Serializable {
        @Override
        public String toString() {
            return "T1";
        }
    }

    int i = new Random().nextInt();
    T1 t3 = new T1() {
        @Override
        public String toString() {
            return "T3";
        }
    };

    @Override
    public void run() {
        class T2 {
            @Override
            public String toString() {
                return "T2";
            }
        }
        System.out.println(i);
        System.out.println(new T1());
        System.out.println(new T2());
        System.out.println(t3);
        new aaa.Test().run();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        OnceMore.produceSaveAndRun(new FileOutputStream("test.dat"), "Test1Write");
    }
}