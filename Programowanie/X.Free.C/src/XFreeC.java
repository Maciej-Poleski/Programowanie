import java.util.Locale;
import java.util.Scanner;

/**
 * User: Maciej Poleski
 * Date: 24.02.12
 * Time: 22:24
 */
public class XFreeC {
    public static void main(String... args)
    {
        Scanner scanner=new Scanner(System.in);
        scanner.useLocale(Locale.ROOT);
        if(scanner.hasNextInt())
        {
            int a=scanner.nextInt();
            int b=scanner.nextInt();
            System.out.print(a+b);
        }
        else
        {
            double a=scanner.nextDouble();
            double b=scanner.nextDouble();
            System.out.format("%1$.2f",a-b);
        }
    }
}
