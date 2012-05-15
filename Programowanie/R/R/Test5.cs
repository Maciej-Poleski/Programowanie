using System;
using System.Collections.Generic;

public class Test5
{
    public static void Test()
    {
        IEnumerable<int> a = new int[] { 10, 20, 30 };
        a.StoreCT();
        for (int i = 0; i < 3; i++)
            Console.WriteLine(0.RestoreCT());
        try
        {
            0.RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ints to restore");
        }
        a.StoreCT();
        40.StoreRT();
        foreach (int i in a.RestoreCT())
            Console.WriteLine(i);
        try
        {
            0.RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ints to restore");
        }
        foreach (int i in a.RestoreCT())
            Console.WriteLine(i);
        a.StoreRT();
        try
        {
            0.RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ints to restore");
        }
        Console.WriteLine(a.RestoreRT());
    }
}