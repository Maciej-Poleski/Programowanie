using System;

public class Test1
{
    public static void Test(string[] args)
    {
        BlindAbacus test = new BlindAbacus(2, 2);
        test[0].add(1);
        Console.WriteLine(test);
        try
        {
            test[0].add(1);
        }
        catch (BlindAbacus.IncorrectRowValueException)
        {
            Console.WriteLine("Row doesn't fit that many");
        }
        Console.WriteLine(test);
        test[0].remove(1);
        try
        {
            test[0].remove(1);
        }
        catch (BlindAbacus.IncorrectRowValueException)
        {
            Console.WriteLine("Row doesn't fit that few");
        }
        Console.WriteLine(test);
    }
}