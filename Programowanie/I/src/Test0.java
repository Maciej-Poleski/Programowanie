import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * User: Maciej Poleski
 * Date: 07.04.12
 * Time: 13:16
 */
public class Test0 {
    public static void main(String... args) throws IOException {
        MessageListener pl = new MessageListener(3);
        PipedWriter pw[] = new PipedWriter[]{new PipedWriter(), new PipedWriter(), new PipedWriter()};
        for (int i = 0; i < 3; i++)
            try {
                pl.listen(new PipedReader(pw[i]));
            } catch (IOException e) {
            }
        send(pw[0], "Pierwszy test1", 111);
        send(pw[0], "Pierwszy test2", 121);
        send(pw[0], "Pierwszy test4", 111);
        send(pw[0], "Pierwszy test3", 131);
    }

    private static void send(PipedWriter pipedWriter, String s, int i) throws IOException {
        pipedWriter.write(s.length() & 0xff);
        pipedWriter.write((s.length() >> 8) & 0xff);
        for (int ii = s.length() - 1; ii >= 0; --ii)
            pipedWriter.write(s.charAt(ii));
        pipedWriter.write(i & 0xff);
        pipedWriter.write((i >> 8) & 0xff);
        pipedWriter.write((i >> 16) & 0xff);
        pipedWriter.write((i >> 24) & 0xff);
        pipedWriter.flush();
    }
}
