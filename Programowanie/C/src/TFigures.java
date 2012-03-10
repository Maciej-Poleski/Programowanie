/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:47
 */
public class TFigures {
    private TFigures() {
    }

    public static boolean sameType(Object a, Object b) {
        if (!(a instanceof TFigure) || !(b instanceof TFigure))
            return false;
        return a.getClass().isInstance(b) || b.getClass().isInstance(a);
    }

    public static boolean areParallel(TLine a, TLine b) {
        return a.a * b.b - b.a * a.b == 0;
    }

    public static double distance(TPoint x, TPoint y) {
        return Math.hypot(x.x - y.x, x.y - y.y);
    }

    public static double distance(TPoint x, TLine line) {
        return Math.abs(line.a * x.x + line.b * x.y + line.c) / Math.hypot(line.a, line.b);
    }
}
