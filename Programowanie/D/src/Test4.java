/**
 * User: Maciej Poleski
 * Date: 10.03.12
 * Time: 18:46
 */
public class Test4 {
    public static void main(String[] args) {
        a a = new a();
        a.a(1).a(5).a(2).a(4).a(3);
        System.out.println(a);
        System.out.println(a.a);
        a.a(a.a());
        System.out.println(a);
        System.out.println(a.a);
        a.a(a.a());
        System.out.println(a);
        System.out.println(a.a);
        a.a(a.a()).a(a.a()).a(a.a());
        System.out.println(a);
        System.out.println(a.a);
        a.a(1).a(5);
        System.out.println(a);
        System.out.println(a.a);
        a.a(a.a());
        System.out.println(a);
        System.out.println(a.a);
        a.a(a.a()).a(a.a()).a(a.a());
        System.out.println(a);
        System.out.println(a.a);
    }
}