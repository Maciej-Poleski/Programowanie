/**
 * User: Maciej Poleski
 * Date: 03.03.12
 * Time: 16:23
 */
public class Test0 {
    public static int main(String... args) {
        Polynomial p = new Polynomial();
        System.out.println(p);
        p = Polynomial.multiply(p, p);
        System.out.println(p);
        p.shiftOy(5);
        System.out.println(p);
        p.shiftOx(5);
        System.out.println(p);
        p = Polynomial.multiply(p, new Polynomial(-1));
        System.out.println(p + " has value " + p.valueAt(2L) + " at 2" );
        p = new Polynomial(0);
        System.out.println(p + " has value " + p.valueAt(2L) + " at 2" );
        return 1;
    }
}