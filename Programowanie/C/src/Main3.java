/**
 * User: Maciej Poleski
 * Date: 09.03.12
 * Time: 16:33
 */
public class Main3{
    public static void main(String args[]){
        TPoint X = new TPoint(-1,3);
        TPoint Y = new TPoint(1,7);
        TPoint Z = new TPoint(3,0);
        TLine l = new TLine(new TPoint(0,0),new TPoint(3,1));
        System.out.println(l);
        System.out.println(X.symmetry(l));
        System.out.println(Y.symmetry(l));
        System.out.println(Z.symmetry(l));
        System.out.println(l.perpendicularLine(X));
        System.out.println(l.perpendicularLine(Y));
        System.out.println(l.perpendicularLine(Z));
    }
}