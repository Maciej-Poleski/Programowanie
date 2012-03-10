/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:47
 */
public class TFigures {
    private TFigures() {
    }

    public static boolean sameType(Object a, Object b) {
        return !(!(a instanceof TFigure) || !(b instanceof TFigure)) && (a.getClass().isInstance(b) || b.getClass().isInstance(a));
    }

    public static boolean areParallel(TLine a, TLine b) {
        double x1 = a.b.x - a.a.x;
        double y1 = a.b.y - a.a.y;
        double x2 = b.b.x - b.a.x;
        double y2 = b.b.y - b.a.y;
        return x1 * y2 == y1 * x2;
    }

    public static double distance(TPoint x, TPoint y) {
        return Math.hypot(x.x - y.x, x.y - y.y);
    }

    public static double distance(TPoint x, TLine line) {
        return distance(x, TLine.crossPoint(line, line.perpendicularLine(x)));
    }
}
