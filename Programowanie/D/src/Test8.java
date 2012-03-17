/**
 * User: Maciej Poleski
 * Date: 10.03.12
 * Time: 18:47
 */
public class Test8 {
    public static void main(String[] args) {
        a o = new a();
        o.a(1);
        o.a(3);
        o.a(2);
        for(a a : o) System.out.println(a);
        for(a a : o.a) System.out.println(a);
    }
}