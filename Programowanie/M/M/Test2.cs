using System;

namespace M
{
    internal class Test2
    {
        public void Test()
        {
            Polygon p = new Triangle(new Point(0, 0), new Point(100, 2), new Point(50, 50));
            Console.WriteLine(p.Length); // = 3
            p.Length = 5;
            Console.WriteLine(p.Length); // = 3
        }
    }
}