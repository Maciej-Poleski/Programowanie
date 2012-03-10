/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:38
 */
public class TTriangle extends TFigure {
    private final TPoint a;
    private final TPoint b;
    private final TPoint c;

    public TTriangle(TPoint a, TPoint b, TPoint c) {
        this.a = a.makeCopy();
        this.b = b.makeCopy();
        this.c = c.makeCopy();
    }

    public TPoint orthocenter() {
        TLine firstLine = new TLine(a, b).perpendicularLine(c);
        TLine secondLine = new TLine(b, c).perpendicularLine(a);
        return TLine.crossPoint(firstLine, secondLine);
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
    public String toString() {
        return "triangle:\n " + a.toString() + "\n " + b.toString() + "\n " + c.toString();
    }

    public TCircle outCircle() {
        TPoint AtoB = new TPoint((a.x + b.x) / 2, (a.y + b.y) / 2);
        TPoint BtoC = new TPoint((b.x + c.x) / 2, (b.y + c.y) / 2);
        TLine firstLine = new TLine(a, b).perpendicularLine(AtoB);
        TLine secondLine = new TLine(b, c).perpendicularLine(BtoC);
        TPoint center = TLine.crossPoint(firstLine, secondLine);
        double radius = TFigures.distance(center, b);
        return new TCircle(center, radius);
    }
}
