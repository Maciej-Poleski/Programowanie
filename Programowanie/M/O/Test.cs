using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace O
{
    class Test
    {
        static void Main()
        {
            {
                Poset p = new Poset(2);
                Poset q = new Poset(2);
                p.add(0, 1);
                q.add(0, 1);
                Console.Write(p * q);
            }
            Console.WriteLine();
            {
                Poset p = new Poset(2);
                Poset q = new Poset(4);
                p.add(0, 1);
                q.add(2, 3);
                Console.Write(p + q);
            }
            Console.WriteLine();
            {
                Poset p = new Poset(2);
                Poset q = new Poset(3);
                p.add(0, 1);
                q.add(1, 2);
                Console.Write(p + q);
            }
            Console.WriteLine();
            {
                Poset p = new Poset(2);
                Poset q = new Poset(3);
                p.add(0, 1);
                q.add(1, 0);
                try
                {
                    Console.Write(p + q);
                }
                catch (ArithmeticException)
                {
                    Console.WriteLine("Error");
                }
            }
            Console.WriteLine();
            {
                Poset ww = new Poset(3);
                Console.Write(ww);
                ww.add(0, 1);
                Console.Write(ww);
                ww.add(1, 2);
                Console.Write(ww);
            }
            Console.ReadKey();
        }
    }
}
