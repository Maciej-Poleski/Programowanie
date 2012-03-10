/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:33
 */
public class Main2{
    public static void main(String args[]){
        TPoint X = new TPoint(10,0);
        TPoint Y = new TPoint(5,11);
        TPoint Z = new TPoint(0,0);
        TFigure t[] = new TFigure[] {new TTriangle(X,Y,Z), new TRectangle(Z,5,10), new TLine(X,Z)};
        for(TFigure f : t) f.move(1,-1);
        TLine l = new TLine(Y,Z);
        System.out.println(l);
        for(TFigure f : t) System.out.println(f.symmetry(l));
        for(TFigure f : t) System.out.println(f);
    }
}