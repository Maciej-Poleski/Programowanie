using System;

public class Test4
{
    public static void Test()
    {
        object o = 1;
        o.StoreRT();
        o = "ala";
        o.StoreRT();
        o = 2;
        o.StoreRT();
        o = "ma";
        o.StoreRT();
        o = 3;
        o.StoreRT();
        o = "kota";
        o.StoreRT();
        try
        {
            new object().RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No objects to restore");
        }
        for (int i = 0; i < 3; i++)
        {
            Console.WriteLine("?".RestoreCT());
            Console.WriteLine(44.RestoreCT());
        }
    }
}