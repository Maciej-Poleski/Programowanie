using System;
using System.Text;

public class Test0A
{
    public string usualString = "Ala";
    public Unique<string> unusualString = "ma kota";

    public override string ToString()
    {
        return usualString + " " + unusualString;
    }
}

public class Test0B
{
    public int[] usualArray = new int[] { 1, 2, 3, 4 };
    public Unique<int[]> unusualArray = new int[] { 5, 6, 7, 8 };

    protected StringBuilder TSA(int[] ar, StringBuilder sB)
    {
        foreach (int i in ar)
            sB.Append(i).Append(", ");
        return sB;
    }
    public override string ToString()
    {
        StringBuilder sB = new StringBuilder();
        return TSA(unusualArray, TSA(usualArray, sB)).ToString();
    }
}

public class Test0
{
    public static void Test(params string[] args)
    {
        Test0A a = new Test0A();
        Console.WriteLine(a);
        a.unusualString += " albo i dwa";
        Console.WriteLine(a);
        Console.WriteLine(new Test0B());
        Console.WriteLine(a.unusualString.Content);
        Console.WriteLine(a.unusualString.Content.GetType() == typeof(string));
    }
}