using System;
using System.Globalization;
using System.Text;

public class Point
{
    private readonly double _x;

    private readonly double _y;

    public Point(double x, double y)
    {
        _x = x;
        _y = y;
    }

    public double X
    {
        get { return _x; }
        set { }
    }

    public double Y
    {
        get { return _y; }
        set { }
    }

    public static Point operator +(Point a, Point b)
    {
        return new Point(a.X + b.X, a.Y + b.Y);
    }

    public static Point operator -(Point a, Point b)
    {
        return new Point(a.X - b.X, a.Y - b.Y);
    }

    public override string ToString()
    {
        return "(" + _x.ToString("F", CultureInfo.InvariantCulture) + "," +
               _y.ToString("F", CultureInfo.InvariantCulture) + ")";
    }
}

public class Polygon
{
    internal Point[] Points;

    public Polygon(params Point[] points)
    {
        Points = new Point[points.Length];
        for (int i = 0; i < points.Length; ++i)
            Points[i] = new Point(points[i].X, points[i].Y);
    }

    public int Length
    {
        get { return Points.Length; }
        set { }
    }

    public double Area
    {
        get
        {
            var preparedPoints = new Point[Points.Length + 1];
            for (int i = 0; i < Points.Length; ++i)
                preparedPoints[i] = Points[i];
            preparedPoints[Points.Length] = Points[0];
            double result = 0;
            for (int i = 0; i < Points.Length; ++i)
                result += preparedPoints[i].X*preparedPoints[i + 1].Y - preparedPoints[i + 1].X*preparedPoints[i].Y;
            return Math.Abs(result/2);
        }
        set { }
    }

    public virtual Point this[int i]
    {
        get
        {
            if (i < 0 || i >= Length) return Points[0];
            else return Points[i];
        }
        set
        {
            double oldArea = Area;
            int selectedPoint;
            if (i < 0 || i >= Length)
            {
                selectedPoint = 0;
            }
            else
            {
                selectedPoint = i;
            }
            Point oldPoint = Points[selectedPoint];
            Points[selectedPoint] = value;
            if (Area > oldArea*2)
                Points[selectedPoint] = oldPoint;
        }
    }

    public override sealed string ToString()
    {
        var result = new StringBuilder("Wielokat  :");
        foreach (Point point in Points)
        {
            result.Append(" " + point);
        }
        return result.ToString();
    }
}

public class Quadrangle : Polygon
{
    public Quadrangle(Point a, Point b, Point c, Point d)
        : base(new Point(a.X, a.Y), new Point(b.X, b.Y), new Point(c.X, c.Y), new Point(d.X, d.Y))
    {
    }

    public double A
    {
        get
        {
            return Math.Sqrt((Points[0].X - Points[1].X)*(Points[0].X - Points[1].X) +
                             (Points[0].Y - Points[1].Y)*(Points[0].Y - Points[1].Y));
        }
        set { }
    }

    public double B
    {
        get
        {
            return Math.Sqrt((Points[1].X - Points[2].X)*(Points[1].X - Points[2].X) +
                             (Points[1].Y - Points[2].Y)*(Points[1].Y - Points[2].Y));
        }
        set { }
    }

    public double C
    {
        get
        {
            return Math.Sqrt((Points[2].X - Points[3].X)*(Points[2].X - Points[3].X) +
                             (Points[2].Y - Points[3].Y)*(Points[2].Y - Points[3].Y));
        }
        set { }
    }

    public double D
    {
        get
        {
            return Math.Sqrt((Points[3].X - Points[0].X)*(Points[3].X - Points[0].X) +
                             (Points[3].Y - Points[0].Y)*(Points[3].Y - Points[0].Y));
        }
        set { }
    }

    public new virtual string ToString()
    {
        var result = new StringBuilder("Czworokat :");
        foreach (Point point in Points)
        {
            result.Append(" " + point);
        }
        return result.ToString();
    }
}

public class Rectangle : Quadrangle
{
    public Rectangle(Point a, double width, double height)
        : base(
            new Point(a.X, a.Y), new Point(a.X + width, a.Y), new Point(a.X + width, a.Y + height),
            new Point(a.X, a.Y + height))
    {
    }

    public override Point this[int i]
    {
        set
        {
            var move = new Point(value.X - this[i].X, value.Y - this[i].Y);
            for (int it = 0; it < Points.Length; ++it)
                Points[it] = new Point(Points[it].X + move.X, Points[it].Y + move.Y);
        }
    }

    public override string ToString()
    {
        return "Prostokat : " + Points[0] + " a=" + A.ToString("F", CultureInfo.InvariantCulture) + " b=" +
               B.ToString("F", CultureInfo.InvariantCulture);
    }
}

public class Square : Rectangle
{
    public Square(Point a, double edge) : base(a, edge, edge)
    {
    }

    public override string ToString()
    {
        return "Kwadrat   :" + Points[0] + " a=" + A.ToString("F", CultureInfo.InvariantCulture);
    }
}

public class Trapezium : Quadrangle
{
    public Trapezium(Point a, double aLength, Point b, double bLength)
        : base(new Point(a.X, a.Y), new Point(a.X + aLength, a.Y), new Point(b.X + bLength, b.Y), new Point(b.X, b.Y))
    {
    }

    public override Point this[int i]
    {
        set
        {
            var move = new Point(value.X - this[i].X, value.Y - this[i].Y);
            if (i == 2 || i == 3)
            {
                Points[2] = new Point(Points[2].X + move.X, Points[2].Y + move.Y);
                Points[3] = new Point(Points[3].X + move.X, Points[3].Y + move.Y);
            }
            else
            {
                Points[0] = new Point(Points[0].X + move.X, Points[0].Y + move.Y);
                Points[1] = new Point(Points[1].X + move.X, Points[1].Y + move.Y);
            }
        }
    }

    public override string ToString()
    {
        return "Trapez    : " + Points[0] + " a=" + A.ToString("F", CultureInfo.InvariantCulture) + " " + Points[3] +
               " b=" + C.ToString("F", CultureInfo.InvariantCulture) + " h=" +
               Math.Abs(Points[0].Y - Points[3].Y).ToString("F", CultureInfo.InvariantCulture);
    }
}

public class Triangle : Polygon
{
    public Triangle(Point a, Point b, Point c)
        : base(new Point(a.X, a.Y), new Point(b.X, b.Y), new Point(c.X, c.Y))
    {
    }

    public double A
    {
        get
        {
            return Math.Sqrt((Points[0].X - Points[1].X)*(Points[0].X - Points[1].X) +
                             (Points[0].Y - Points[1].Y)*(Points[0].Y - Points[1].Y));
        }
        set { }
    }

    public double B
    {
        get
        {
            return Math.Sqrt((Points[1].X - Points[2].X)*(Points[1].X - Points[2].X) +
                             (Points[1].Y - Points[2].Y)*(Points[1].Y - Points[2].Y));
        }
        set { }
    }

    public double C
    {
        get
        {
            return Math.Sqrt((Points[2].X - Points[0].X)*(Points[2].X - Points[0].X) +
                             (Points[2].Y - Points[0].Y)*(Points[2].Y - Points[0].Y));
        }
        set { }
    }

    public new virtual string ToString()
    {
        var result = new StringBuilder("Trojkat   :");
        foreach (Point point in Points)
        {
            result.Append(" " + point);
        }
        return result.ToString();
    }
}

public class RightAngledTriangle : Triangle
{
    public RightAngledTriangle(Point a, double width, double height)
        : base(new Point(a.X, a.Y), new Point(a.X + width, a.Y), new Point(a.X, a.Y + height))
    {
    }

    public override Point this[int i]
    {
        set
        {
            var move = new Point(value.X - this[i].X, value.Y - this[i].Y);
            for (int it = 0; it < Points.Length; ++it)
                Points[it] = new Point(Points[it].X + move.X, Points[it].Y + move.Y);
        }
    }

    public override string ToString()
    {
        return "Trojkat prostokatny   : a=" + A.ToString("F", CultureInfo.InvariantCulture) + " b=" +
               C.ToString("F", CultureInfo.InvariantCulture) + " c=" + B.ToString("F", CultureInfo.InvariantCulture);
    }
}

public class IsoscalesTraingle : Triangle
{
    public IsoscalesTraingle(Point a, double width, double height)
        : base(new Point(a.X, a.Y), new Point(a.X + width, a.Y), new Point(a.X + width/2, a.Y + height))
    {
    }

    public override Point this[int i]
    {
        set
        {
            var move = new Point(value.X - this[i].X, value.Y - this[i].Y);
            for (int it = 0; it < Points.Length; ++it)
                Points[it] = new Point(Points[it].X + move.X, Points[it].Y + move.Y);
        }
    }

    public override string ToString()
    {
        return "Trojkat rownoramienny : a=" + B.ToString("F", CultureInfo.InvariantCulture) + " b=" +
               A.ToString("F", CultureInfo.InvariantCulture);
    }
}

public class EquilaTetralTringle : IsoscalesTraingle
{
    public EquilaTetralTringle(Point a, double edge) : base(new Point(a.X, a.Y), edge, edge*Math.Sqrt(3)/2)
    {
    }

    public override string ToString()
    {
        return "Trojkat rownoboczny   : a=" + A.ToString("F", CultureInfo.InvariantCulture);
    }
}