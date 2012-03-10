/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:40
 */
public class TLine extends TFigure implements Cloneable {
    double a;
    double b;
    double c;

    public TLine(TPoint a, TPoint b) {
        double x1 = a.x;
        double y1 = a.y;
        double x2 = b.x;
        double y2 = b.y;
        this.a = (y1 - y2);
        this.b = (x2 - x1);
        this.c = x1 * (y2 - y1) - y1 * (x2 - x1);
    }

    private TLine(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public TLine perpendicularLine(TPoint point) {
        double x = point.x;
        double y = point.y;
        return new TLine(-b, a, b * x - a * y);
    }

    @Override
    public void move(double x, double y) {
        this.c -= (this.a * x + this.b * y);
    }

    @Override
    public TLine symmetry(TLine line) {
        // Nowy algorytm - zbyt mała precyzja
        TPoint aPoint;
        TPoint bPoint;
        if (a == 0) {
            aPoint = new TPoint(0, c / b);
            bPoint = new TPoint(100, c / b);
        } else if (b == 0) {
            aPoint = new TPoint(c / a, 0);
            bPoint = new TPoint(c / a, 100);
        } else {
            aPoint = new TPoint(0, c / b);
            bPoint = new TPoint(c / a, 0);
        }
        return new TLine(aPoint.symmetry(line), bPoint.symmetry(line));
    }

    @Override
    protected TLine clone() {
        try {
            return (TLine) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("line: ");
        if (Math.round(100 * b) / 100.0 == 0) {
            result.append("X = " + Math.round(-100 * c / a) / 100.0);
            return result.toString();
        } else {
            result.append("Y = " + Math.round(-100 * a / b) / 100.0 + "X + " + Math.round(-100 * c / b) / 100.0);
            return result.toString();
        }
    }

    private static TPoint crossPoint(TLine k, TLine l) {
        double Wab = k.a * l.b - l.a * k.b;
        double Wbc = k.b * l.c - l.b * k.c;
        double Wca = k.clone().a - l.c * k.a;
        return new TPoint(Wbc / Wab, Wca / Wab);
    }
}
