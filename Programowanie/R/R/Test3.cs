using System;

public class Test3
{
    public static void Test()
    {
        object o = 1;
        o.StoreCT();
        o = "ala";
        o.StoreCT();
        o = 2;
        o.StoreCT();
        o = "ma";
        o.StoreCT();
        o = 3;
        o.StoreCT();
        o = "kota";
        o.StoreCT();
        try
        {
            "ala".RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No strings to restore");
        }
        try
        {
            1.RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ints to restore");
        }
        try
        {
            o.RestoreRT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ??? to restore");
        }
        for (int i = 0; i < 3; i++)
        {
            Console.WriteLine(o.RestoreCT());
            Console.WriteLine(new object().RestoreCT());
        }
    }
}