import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: Maciej Poleski
 * Date: 13.04.12
 * Time: 21:43
 */
public class ZipToMD5 {
    public static void scanZip(String filename) throws FileNotFoundException {
        class Entry implements Comparable<Entry> {
            Entry(String digest, double ratio, String name) {
                this.digest = digest;
                this.ratio = ratio;
                this.name = name;
            }

            String digest;
            double ratio;
            String name;

            @Override
            public int compareTo(Entry o) {
                return name.compareTo(o.name);
            }
        }
        List<Entry> result = new ArrayList<Entry>();
        InputStream inputStream = new FileInputStream(filename);
        ZipInputStream stream;
        stream = new ZipInputStream(inputStream);
        for (; ; ) {
            ZipEntry entry = null;
            try {
                entry = stream.getNextEntry();
            } catch (IOException ignored) {
            }
            if (entry == null) {
                break;
            }
            if (entry.isDirectory()) {
                continue;
            }
            double ratio = ((double) entry.getCompressedSize()) / entry.getSize() * 100;
            String name = entry.getName();
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ignored) {
            }
            byte[] rawInput = new byte[8192];
            int partSize;
            try {
                while ((partSize = stream.read(rawInput)) > 0) {
                    md.update(rawInput, 0, partSize);
                }
            } catch (IOException ignored) {
            }
            byte[] rawDigest = md.digest();
            String digest = (new BigInteger(1, rawDigest)).toString(16);
            if (digest.length() < 32) {
                digest = "00000000000000000000000000000000000" + digest;
                digest = digest.substring(digest.length() - 32);
            }
            try {
                stream.closeEntry();
            } catch (IOException ignored) {
            }
            result.add(new Entry(digest, ratio, name));
        }
        Collections.sort(result);
        for (Entry e : result) {
            System.out.printf(Locale.ROOT, "%s %.1f%% %s\n", e.digest, e.ratio, e.name);
        }
    }
}