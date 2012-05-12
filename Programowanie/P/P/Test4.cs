using System;

public class Test4
{
    public static void Test()
    {
        BlindAbacus test = new BlindAbacus(7, 7);
        for (int i = 0; i < 7; i++)
            test[i].add(i);
        Console.WriteLine(test);
        int line = 0;
        foreach (BlindAbacus.Row r in test)
        {
            Console.WriteLine(++line + "outer :" + r);
            if (line == 5) try
                {
                    foreach (BlindAbacus.Row s in test)
                        Console.WriteLine(" inner :" + s);
                }
                catch (BlindAbacus.SomeoneThereException)
                {
                    Console.WriteLine("Someone there");
                }
        }
        foreach (BlindAbacus.Row r in test)
            Console.WriteLine(" outer :" + r);
    }
}