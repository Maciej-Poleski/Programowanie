/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:32
 */
public class Main1{
    public static void main(String args[]){
        TPoint X = new TPoint(10,10);
        TPoint Y = new TPoint(5.3,11.7);
        TFigure t[] = new TFigure[] {new TTriangle(new TPoint(0,0),X,Y),
                new TSquare(X,5.1),
                new TRectangle(Y,5,10),
                new TCircle(Y, 10),
                new TLine(X, Y),
        };
        System.out.println(X);
        System.out.println(Y);
        for(TFigure f : t) System.out.println(f);
        X.move(-10,-10);
        Y.move(1,1);
        System.out.println(X);
        System.out.println(Y);
        for(TFigure f : t) System.out.println(f);
    }
}