import java.util.Arrays;

/**
 * User: Maciej Poleski
 * Date: 10.03.12
 * Time: 18:47
 */
public class Test6 {
    public static void main(String[] args) {
        a a = new a();
        a.a(1).a(5).a(2).a(4).a(3);
        System.out.println(Arrays.toString(new int[]{a.a(), a.a(), a.a(), a.a(), a.a()}));
    }
}