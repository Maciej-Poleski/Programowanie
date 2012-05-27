using System;

namespace M
{
    internal class Test1
    {
        public void Test()
        {
            var p = new Point(1.0, 7.5);
            Console.WriteLine(p);
            Console.WriteLine(p.X + " " + p.Y);
            p.X++;
            p.Y++;
            Console.WriteLine(p.X + " " + p.Y);
            Console.WriteLine((p + new Point(1, 1)) + "   " + (p - new Point(1, 1)));
        }
    }
}