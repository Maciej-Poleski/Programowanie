/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:38
 */
public class TTriangle extends TFigure {
    TPoint a;
    TPoint b;
    TPoint c;

    public TTriangle(TPoint a, TPoint b, TPoint c) {
        this.a = a.clone();
        this.b = b.clone();
        this.c = c.clone();
    }

    public TPoint orthocenter() {
        return crossPoint(new TLine(a, b).perpendicularLine(c), new TLine(b, c).perpendicularLine(a));
    }

    @Override
    public double area() {
        double a1 = a.x;
        double a2 = a.y;
        double b1 = b.x;
        double b2 = b.y;
        double c1 = c.x;
        double c2 = c.y;
        return Math.abs(a1 * b2 + b1 * c2 + c1 * a2 - c1 * b2 - a1 * c2 - b1 * a2) / 2;
    }

    @Override
    public void move(double x, double y) {
        a.move(x, y);
        b.move(x, y);
        c.move(x, y);
    }

    @Override
    public TFigure symmetry(TLine line) {
        return new TTriangle(a.symmetry(line), b.symmetry(line), c.symmetry(line));
    }

    @Override
    protected TTriangle clone() {
        try {
            TTriangle result = (TTriangle) super.clone();
            result.a = a.clone();
            result.b = b.clone();
            result.c = c.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "triangle:\n " + a.toString() + "\n " + b.toString() + "\n " + c.toString();
    }

    public TCircle outCircle() {
        // FIXME - mała precyzja
        return new TCircle(crossPoint(new TLine(a, b).perpendicularLine(new TPoint((a.x + b.x) / 2, (a.y + b.y) / 2)),
                new TLine(b, c).perpendicularLine(new TPoint((b.x + c.x) / 2, (b.y + c.y) / 2))),
                TFigures.distance(a, crossPoint(new TLine(a, b).perpendicularLine(new TPoint((a.x + b.x) / 2, (a.y + b.y) / 2)),
                        new TLine(b, c).perpendicularLine(new TPoint((b.x + c.x) / 2, (b.y + c.y) / 2)))));
    }

    private static TPoint crossPoint(TLine k, TLine l) {
        double Wab = k.a * l.b - l.a * k.b;
        double Wbc = k.b * l.c - l.b * k.c;
        double Wca = k.clone().a - l.c * k.a;
        return new TPoint(Wbc / Wab, Wca / Wab);
    }
}
