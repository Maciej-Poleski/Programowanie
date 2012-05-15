using System;
using System.Collections.Generic;

public class Test6
{
    public static void Test(params string[] args)
    {
        IEnumerable<object> temp = new object[3] { 1, "ala", null };
        temp.StoreCT();
        try
        {
            0.RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No ints to restore");
        }
        try
        {
            "?".RestoreCT();
        }
        catch (EZStorage.EmptyException)
        {
            Console.WriteLine("No strings to restore");
        }
        Console.WriteLine(new object().RestoreRT());
        Console.WriteLine(new object().RestoreRT());
        Console.WriteLine(new object().RestoreRT());
    }
}