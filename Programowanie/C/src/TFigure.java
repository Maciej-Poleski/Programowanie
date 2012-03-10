/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:37
 */
public abstract class TFigure {
    public double area() {
        return 0;
    }

    public abstract void move(double x, double y);

    public abstract TFigure symmetry(TLine line);
}
