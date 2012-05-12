using System;
using System.Collections.Generic;

public class Test0
{
    public static void Test(string[] args)
    {
        BlindAbacus test = new BlindAbacus(4, 4);
        Console.WriteLine(test);
        Console.WriteLine();
        foreach (BlindAbacus.Row temp in test)
        {
            temp.add(2);
        }
        Console.WriteLine(test);
        Console.WriteLine();
        test[1].add(1);
        test[0].remove(1);
        Console.WriteLine(test);
        Console.WriteLine("Value " + (int)test);
    }
}
