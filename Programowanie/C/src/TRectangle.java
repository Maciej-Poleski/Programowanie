/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:39
 */
public class TRectangle extends TFigure {
    final TPoint a;
    final TPoint b;
    final TPoint c;
    final TPoint d;

    public TRectangle(TPoint a, double width, double height) {
        this.a = a.makeCopy();
        this.b = a.makeCopy();
        this.c = a.makeCopy();
        this.d = a.makeCopy();
        b.move(0, height);
        c.move(width, height);
        d.move(width, 0);
    }

    private TRectangle(TPoint a, TPoint b, TPoint c, TPoint d) {
        this.a = a.makeCopy();
        this.b = b.makeCopy();
        this.c = c.makeCopy();
        this.d = d.makeCopy();
    }

    public TCircle outCircle() {
        return new TCircle(new TPoint((a.x + c.x) / 2, (a.y + c.y) / 2),
                TFigures.distance(a, new TPoint((a.x + c.x) / 2, (a.y + c.y) / 2)));
    }

    @Override
    public double area() {
        return Math.abs((a.x - c.x) * (a.y - c.y));
    }

    @Override
    public void move(double x, double y) {
        a.move(x, y);
        b.move(x, y);
        c.move(x, y);
        d.move(x, y);
    }

    @Override
    public TRectangle symmetry(TLine line) {
        return new TRectangle(a.symmetry(line), b.symmetry(line), c.symmetry(line), d.symmetry(line));
    }

    @Override
    public String toString() {
        return "rectangle:\n " + a.toString() + "\n " + b.toString() + "\n " + c.toString() + "\n " + d.toString();
    }
}
