/**
 * User: Maciej Poleski
 * Date: 17.03.12
 * Time: 20:10
 */
import java.util.Collection;

public class Test0 {
    public static void main(String[] args) {
        String ala = "Ala has a cat";
        //Warning, raw type
        Collection col = SmartFactory.fixIt(Collection.class, ala);
        System.out.println(col);
        try {
            col.iterator();
        }catch(SmartFactory.HellNoException e){
            System.out.println("Well.... doesn't work.");
        }
    }
}

