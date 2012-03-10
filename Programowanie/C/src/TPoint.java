/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:37
 */
public class TPoint extends TFigure {
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
        TPoint crossPoint = TLine.crossPoint(line, line.perpendicularLine(this));
        crossPoint.move(crossPoint.x - this.x, crossPoint.y - this.y);
        return crossPoint;
    }

    TPoint makeCopy() {
        return new TPoint(x,y);
    }

    @Override
    public String toString() {
        return "point: (" + (Math.round(100 * x) / 100.0) + ", " + (Math.round(100 * y) / 100.0) + ')';
    }
}
