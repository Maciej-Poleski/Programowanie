using System;

namespace M
{
    internal class Test3
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
            foreach (Polygon p in list) Console.WriteLine(p.ToString());
            Console.WriteLine("---");
            var czworokaty = new[]
                                 {
                                     new Quadrangle(new Point(-10, -10), new Point(10, -5), new Point(5, 5),
                                                    new Point(0, 10)),
                                     new Rectangle(new Point(-1, -1), 1001, 10),
                                     new Square(new Point(10, 10), 20),
                                     new Trapezium(new Point(0, 0), 5, new Point(-5, 10), 20)
                                 };
            foreach (Quadrangle p in czworokaty) Console.WriteLine(p.ToString());
            Console.WriteLine("---");
            var trojkaty = new[]
                               {
                                   new Triangle(new Point(0, 0), new Point(100, 2), new Point(50, 50)),
                                   new RightAngledTriangle(new Point(0, 0), 20, 30),
                                   new IsoscalesTraingle(new Point(-5, 5), 10, 50),
                                   new EquilaTetralTringle(new Point(-5, -5), 10)
                               };
            foreach (Triangle p in trojkaty) Console.WriteLine(p.ToString());
        }
    }
}