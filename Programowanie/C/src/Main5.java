/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:34
 */
public class Main5{
    public static void main(String args[]){
        TPoint X = new TPoint(3,1);
        TPoint Y = new TPoint(0,10);
        TPoint Z = new TPoint(-3,-1);
        TTriangle t = new TTriangle(X,Y,Z);
        TRectangle r = new TRectangle(X,10,7);
        TSquare s = new TSquare(Y,5);
        System.out.println(TFigures.sameType(t,s));
        System.out.println(TFigures.sameType(r,s));
        System.out.println(TFigures.sameType(r,new Integer(2)));
        TLine l1 = new TLine(X,Y);
        TLine l2 = new TLine(X,Y); l2.move(1,1);
        TLine l3 = new TLine(Y,Z);
        System.out.println(TFigures.areParallel(l1,l2));
        System.out.println(TFigures.areParallel(l1,l3));
        System.out.println(TFigures.distance(new TPoint(0,0),new TPoint(3,4)));
        System.out.println(Math.round(TFigures.distance(X,l2)*100)/100.);
    }
}