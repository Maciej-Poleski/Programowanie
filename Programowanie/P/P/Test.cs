using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace P
{
    class Test
    {
        static void Main()
        {
            Test0.Test(new string[0]);
            Console.WriteLine();
            Test1.Test(new string[0]);
            Console.WriteLine();
            Test2.Test();
            Console.WriteLine();
            Test3.Test();
            Console.WriteLine();
            Test4.Test();
            Console.WriteLine();

            Console.ReadKey();
        }
    }
}
