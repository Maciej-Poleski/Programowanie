/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:37
 */
public class TPoint extends TFigure implements Cloneable {
    double x;
    double y;

    public TPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public TPoint symmetry(TLine line) {
//        STARA IMPLEMENTACJA
//        double lambda = Math.abs(line.a * x + line.b * y + line.c) / (line.a * line.a + line.b * line.b);
//        double xm = line.a * lambda * 2;
//        double ym = line.b * lambda * 2;
//        TPoint result = clone();
//        result.move(xm, ym);
//        if (TFigures.distance(result, line) == TFigures.distance(this, line))
//            return result;
//        else {
//            result = clone();
//            result.move(-xm, -ym);
//            return result;
//        }
        double a = line.a;
        double b = line.b;
        double c = line.c;
        double x1 = this.x;
        double y1 = this.y;
        double x2 = ((b * b - a * a) * x1 - 2 * a * (b * y1 + c)) / (a * a + b * b);
        double y2 = ((a * a - b * b) * y1 - 2 * b * (a * x1 + c)) / (a * a + b * b);
        return new TPoint(x2, y2);
    }

    @Override
    protected TPoint clone() {
        try {
            return (TPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "point: (" + (Math.round(100 * x) / 100.0) + ", " + (Math.round(100 * y) / 100.0) + ')';
    }
}
