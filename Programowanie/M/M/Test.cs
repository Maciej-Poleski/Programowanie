using System;

namespace M
{
    internal class Test
    {
        public static void Main()
        {
            new Test1().Test();
            Console.WriteLine();
            new Test2().Test();
            Console.WriteLine();
            new Test3().Test();
            Console.WriteLine();
            new Test4().Test();

            Console.ReadKey();
        }
    }
}