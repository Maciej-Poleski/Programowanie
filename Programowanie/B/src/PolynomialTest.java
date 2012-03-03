import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * User: Maciej Poleski
 * Date: 03.03.12
 * Time: 14:35
 */
public class PolynomialTest {
    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testValueAt() throws Exception {
        Polynomial p = new Polynomial();
        p.shiftOy(55);
        assertEquals(p.valueAt(1), 56);
        assertEquals(p.valueAt(10), 65);
        assertEquals(p.valueAt(0), 55);
        p.shiftOy(-55);
        assertEquals(p.valueAt(0), 0);
        assertEquals(p.valueAt(1), 1);
        assertEquals(p.valueAt(5), 5);
        p = Polynomial.multiply(p, p);
        p = Polynomial.multiply(p, new Polynomial());
        p = Polynomial.multiply(p, new Polynomial());
        assertEquals(p.toString(), "x^4");
        p.shiftOy(6);
        assertEquals(p.toString(), "x^4 + 6");
        assertEquals(p.valueAt(0), 6);
        assertEquals(p.valueAt(-1), 7);
        assertEquals(p.valueAt(1), 7);
        assertEquals(p.valueAt(-2), 22);
        assertEquals(p.valueAt(10), 10006);
        p.shiftOy(-12);
        p = Polynomial.multiply(p, p);
        assertEquals(p.toString(), "x^8 - 12x^4 + 36");
    }

    @Test
    public void testToString() throws Exception {
        Polynomial p = new Polynomial();
        assertEquals(p.toString(), "x");
    }

    @Test
    public void testShiftOy() throws Exception {
        Polynomial p = new Polynomial();
        p.shiftOy(55);
        assertEquals(p.toString(), "x + 55");
        p.shiftOy(55);
        assertEquals(p.toString(), "x + 110");
        p.shiftOy(-50);
        assertEquals(p.toString(), "x + 60");
        p.shiftOy(-60);
        assertEquals(p.toString(), "x");
        p.shiftOy(-40);
        assertEquals(p.toString(), "x - 40");
        assertEquals(new Polynomial(0).toString(), "");
    }

    @Test
    public void testShiftOx() throws Exception {
        Polynomial p = new Polynomial();
        assertEquals(p.toString(), "x");
        p = Polynomial.multiply(p, p);
        assertEquals(p.toString(), "x^2");
        p.shiftOy(5);
        assertEquals(p.toString(), "x^2 + 5");
        p.shiftOx(5);
        assertEquals(p.toString(), "x^2 - 10x + 30");
    }

    @Test
    public void testMultiply() throws Exception {
        Polynomial p = new Polynomial();
        p = Polynomial.multiply(p, p);
        assertEquals(p.toString(), "x^2");
        p.shiftOy(6);
        Polynomial p2 = Polynomial.multiply(p, new Polynomial(-1));
        assertEquals(p2.toString(), "-x^2 - 6");
        Polynomial p3 = Polynomial.multiply(p2, new Polynomial(0));
        assertEquals(p3.toString(), "");
        p = new Polynomial();
        p.shiftOy(55);
        assertEquals(p.valueAt(1), 56);
        assertEquals(p.valueAt(10), 65);
        assertEquals(p.valueAt(0), 55);
        p.shiftOy(-55);
        assertEquals(p.valueAt(0), 0);
        assertEquals(p.valueAt(1), 1);
        assertEquals(p.valueAt(5), 5);
        p = Polynomial.multiply(p, p);
        p = Polynomial.multiply(p, new Polynomial());
        p = Polynomial.multiply(p, new Polynomial());
        assertEquals(p.toString(), "x^4");
        p.shiftOy(6);
        assertEquals(p.toString(), "x^4 + 6");
        assertEquals(p.valueAt(0), 6);
        assertEquals(p.valueAt(-1), 7);
        assertEquals(p.valueAt(1), 7);
        assertEquals(p.valueAt(-2), 22);
        assertEquals(p.valueAt(10), 10006);
        p.shiftOy(-12);
        p = Polynomial.multiply(p, p);
        assertEquals(p.toString(), "x^8 - 12x^4 + 36");
        assertEquals(Polynomial.multiply(p, p2).toString(), "-x^10 - 6x^8 + 12x^6 + 72x^4 - 36x^2 - 216");
        assertEquals(Polynomial.multiply(p2, p).toString(), "-x^10 - 6x^8 + 12x^6 + 72x^4 - 36x^2 - 216");
        p = Polynomial.multiply(p2, p);
        assertEquals(p.toString(),"-x^10 - 6x^8 + 12x^6 + 72x^4 - 36x^2 - 216");
        p = Polynomial.multiply(p, p);
        assertEquals(p.toString(),"x^20 + 12x^18 + 12x^16 - 288x^14 - 648x^12 + 2592x^10 + 6912x^8 - 10368x^6 - 29808x^4 + 15552x^2 + 46656");
    }
}
