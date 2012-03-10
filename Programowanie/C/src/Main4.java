/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:33
 */
public class Main4{
    public static void main(String args[]){
        TPoint X = new TPoint(3,1);
        TPoint Y = new TPoint(0,10);
        TPoint Z = new TPoint(-3,-1);
        TTriangle t = new TTriangle(X,Y,Z);
        TRectangle r = new TRectangle(X,10,7);
        TSquare s = new TSquare(Y,5);
        System.out.println(t);
        System.out.println(t.orthocenter());
        System.out.println(t.outCircle());
        System.out.println(r.outCircle());
        System.out.println(s.inCircle());
    }
}