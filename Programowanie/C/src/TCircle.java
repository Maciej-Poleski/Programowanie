/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:39
 */
public class TCircle extends TFigure {
    TPoint center;
    double radius;

    public TCircle(TPoint s, double radius) {
        this.center = s.clone();
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    protected TCircle clone() {
        try {
            TCircle result = (TCircle) super.clone();
            result.center = center.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "circle:\n center at " + center.toString() + "\n radius: " + Math.round(100 * radius) / 100.0;
    }

    @Override
    public void move(double x, double y) {
        center.move(x, y);
    }

    @Override
    public TFigure symmetry(TLine line) {
        return new TCircle(center.symmetry(line), radius);
    }
}
