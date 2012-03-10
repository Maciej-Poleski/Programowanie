/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:39
 */
public class TRectangle extends TFigure {
    TPoint a;
    TPoint b;
    TPoint c;
    TPoint d;

    public TRectangle(TPoint a, double width, double height) {
        this.a = a.clone();
        this.b = a.clone();
        this.c = a.clone();
        this.d = a.clone();
        b.move(0, height);
        c.move(width, height);
        d.move(width, 0);
    }

    private TRectangle(TPoint a, TPoint b, TPoint c, TPoint d) {
        this.a = a.clone();
        this.b = b.clone();
        this.c = c.clone();
        this.d = d.clone();
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
    protected TRectangle clone() {
        try {
            TRectangle result = (TRectangle) super.clone();
            result.a = a.clone();
            result.b = b.clone();
            result.c = c.clone();
            result.d = d.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "rectangle:\n " + a.toString() + "\n " + b.toString() + "\n " + c.toString() + "\n " + d.toString();
    }
}
