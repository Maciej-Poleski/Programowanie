import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: Maciej Poleski
 * Date: 14.04.12
 * Time: 19:26
 */
public class TestRead {
    public static void main(String[] args) throws IOException {
        OnceMore.loadAndRun(new FileInputStream("test.dat"));
    }
}