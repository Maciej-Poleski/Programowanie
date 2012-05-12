using System;

public class Test3
{
    public static void Test()
    {
        BlindAbacus test1 = new BlindAbacus(3, 3), test2 = new BlindAbacus(4, 3);
        test1[2].add(2);
        test2[2].add(2);
        Console.WriteLine(test1);
        Console.WriteLine();
        Console.WriteLine(test2);
        try
        {
            BlindAbacus test3 = test1 + test2;
            Console.WriteLine(test3);
        }
        catch (BlindAbacus.ComputationException)
        {
            Console.WriteLine("Incomparable bases");
        }
        Console.WriteLine(test1);
        Console.WriteLine("Value : " + (int)test1);
        test1++;
        Console.WriteLine(test1);
        Console.WriteLine("Value : " + (int)test1);
        for (int i = 0; i < 8; i++) test1++;
        Console.WriteLine(test1);
        Console.WriteLine("Value : " + (int)test1);
        for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < 8; i++) test1--;
            Console.WriteLine(test1);
            Console.WriteLine("Value : " + (int)test1);
        }
        test1--;
        test1--;
        test1--;
        Console.WriteLine("<" + test1 + ">");
        Console.WriteLine("Value : " + (int)test1);
        try
        {
            test1--;
        }
        catch (BlindAbacus.ComputationException)
        {
            Console.WriteLine("Mniej niż zero");
        }
    }
}