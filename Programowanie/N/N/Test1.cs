using System;

public class Test1
{
    public static void Test()
    {
        {
            P.Q.R s = new P.Q.R();
            Console.WriteLine(s.s());
            Console.WriteLine(((P.Q)s).s());
            Console.WriteLine(((P)s).s());
            Console.WriteLine();
        }
        {
            P.Q s = new P.Q();
            Console.WriteLine(s.s());
            Console.WriteLine(((P)s).s());
            Console.WriteLine();
        }
        {
            P s = new P();
            Console.WriteLine(s.s());
        }
    }
}