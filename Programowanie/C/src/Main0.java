/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:30
 */
public class Main0{
    public static void main(String args[]){
        TFigure t[] = new TFigure[] {new TPoint(12,17),
                new TTriangle(new TPoint(0,0),new TPoint(-10,-10),new TPoint(10,-10)),
                new TSquare(new TPoint(0,0),5),
                new TRectangle(new TPoint(0,0),5,10),
                new TCircle(new TPoint(100,100), 10),
                new TLine(new TPoint(10,10), new TPoint(0,3)),
                new TLine(new TPoint(5,0), new TPoint(5,3))
        };

        for(TFigure f : t) System.out.println(f);
        for(TFigure f : t) System.out.println("area equals " + f.area());
    }
}