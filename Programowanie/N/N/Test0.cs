using System;

public class Test0
{
    public static void Test()
    {
        P.Q.R.S s = new P.Q.R.S();
        Console.WriteLine(s.s());
        Console.WriteLine(((P.Q.R)s).s());
        Console.WriteLine(((P.Q)s).s());
        Console.WriteLine(((P)s).s());
    }
}

