/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:38
 */
public class TSquare extends TRectangle {
    public TSquare(TPoint a, double length) {
        super(a, length, length);
    }

    public TCircle inCircle() {
        return new TCircle(new TPoint((a.x + c.x) / 2, (a.y + c.y) / 2),
                TFigures.distance(new TPoint((a.x + c.x) / 2, (a.y + c.y) / 2), new TLine(a, b)));
    }

    @Override
    protected TSquare clone() {
        return (TSquare) super.clone();
    }

    @Override
    public String toString() {
        return "square:\n " + a.toString() + "\n " + b.toString() + "\n " + c.toString() + "\n " + d.toString();
    }
}
