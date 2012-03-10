/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:40
 */
public class TLine extends TFigure {
    final TPoint a;
    final TPoint b;

    public TLine(TPoint a, TPoint b) {
        this.a = a.makeCopy();
        this.b = b.makeCopy();
    }

    public TLine perpendicularLine(TPoint point) {
        TPoint d = point.makeCopy();
        d.move(a.y - b.y, b.x - a.x);
        return new TLine(point, d);
    }

    @Override
    public void move(double x, double y) {
        this.a.move(x, y);
        this.b.move(x, y);
    }

    @Override
    public TLine symmetry(TLine line) {
        return new TLine(a.symmetry(line), b.symmetry(line));
    }

    @Override
    public String toString() {
        double x1 = a.x;
        double y1 = a.y;
        double x2 = b.x;
        double y2 = b.y;
        double a = (y1 - y2);
        double b = (x2 - x1);
        double c = x1 * (y2 - y1) - y1 * (x2 - x1);
        StringBuilder result = new StringBuilder("line: ");
        if (Math.round(100 * b) / 100.0 == 0) {
            result.append("X = ").append(Math.round(-100 * c / a) / 100.0);
            return result.toString();
        } else {
            result.append("Y = " + Math.round(-100 * a / b) / 100.0 + "X + " + Math.round(-100 * c / b) / 100.0);
            return result.toString();
        }
    }

    static TPoint crossPoint(TLine line1, TLine line2) {
        double a1 = line1.a.y - line1.b.y;
        double b1 = line1.b.x - line1.a.x;
        double c1 = line1.a.x * (line1.b.y - line1.a.y) - line1.a.y * (line1.b.x - line1.a.x);
        double a2 = line2.a.y - line2.b.y;
        double b2 = line2.b.x - line2.a.x;
        double c2 = line2.a.x * (line2.b.y - line2.a.y) - line2.a.y * (line2.b.x - line2.a.x);
        double Wab = a1 * b2 - a2 * b1;
        double Wbc = b1 * c2 - b2 * c1;
        double Wca = c1 * a2 - c2 * a1;
        return new TPoint(Wbc / Wab, Wca / Wab);
    }
}
