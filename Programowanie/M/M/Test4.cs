using System;

namespace M
{
    internal class Test4
    {
        public void Test()
        {
            var list = new[]
                           {
                               new Polygon(new Point(0, 0), new Point(10, 1), new Point(15, 5), new Point(5, 10),
                                           new Point(0, 10)),
                               new Quadrangle(new Point(-10, -10), new Point(10, -5), new Point(5, 5), new Point(0, 10))
                               ,
                               new Rectangle(new Point(-1, -1), 100, 10),
                               new Square(new Point(10, 10), 20),
                               new Trapezium(new Point(0, 0), 5, new Point(-5, 10), 20),
                               new Triangle(new Point(0, 0), new Point(100, 2), new Point(50, 50)),
                               new RightAngledTriangle(new Point(0, 0), 20, 30),
                               new IsoscalesTraingle(new Point(-5, 5), 10, 50),
                               new EquilaTetralTringle(new Point(-5, -5), 10)
                           };

            foreach (Polygon p in list) Console.WriteLine(p + " area = " + p.Area);
            Console.WriteLine("---");
            foreach (Polygon p in list) p[0] = new Point(3, 3);
            foreach (Polygon p in list) Console.WriteLine(p + " area = " + p.Area);
            var q = new Polygon(new Point(0, 0), new Point(10, 1), new Point(15, 5), new Point(5, 10), new Point(0, 10));
            q.Area = 0;
            Console.WriteLine(q.Area);
            q[1] = new Point(1000, 1);
            Console.WriteLine(q.Area);
            var t = new Triangle(new Point(0, 0), new Point(100, 0), new Point(50, 50));
            Console.WriteLine("Dlugosci bokow trojkata : {0:F2} {1:F2} {2:F2}", t.A, t.B, t.C);
            Console.WriteLine("Wspolrzedne pierwszego wierzcholka: " + t[0]);
            t[0].X++;
            t[0].Y = 10;
            Console.WriteLine("Wspolrzedne pierwszego wierzcholka: " + t[0]);
        }
    }
}