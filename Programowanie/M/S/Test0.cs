using System;
class Test0
{
    static void Main(string[] args)
    {
        CyclicList<string> cL = new CyclicList<string>("Ala", "ma", "kota");
        CyclicList<string> cLtemp = cL;
        for (int i = 0; i < 2 * cL.Length; i++) Console.WriteLine(cL[i]);
        cL[2] = "kociaki";
        Console.WriteLine(cL);
        cL = cL << 1;
        cL[1] = "koty";
        Console.WriteLine(cL);
        Console.WriteLine(cLtemp);
        cL = cL >> 2;
        Console.WriteLine(cL);
        string[] sA = cL;
        foreach (string s in sA)
            Console.WriteLine(s);
        CyclicList<string> str = new CyclicList<string>("Ala", "ma", "kota", "Sam", "worek");
        foreach (var C in str.split(n => n[1])) Console.WriteLine(C);
        CyclicList<int> cI = new CyclicList<int>(0, 1, 2, 3, 4, 5, 6);
        foreach (var C in (cI >> 1).split(n => 1 - n % 2)) Console.WriteLine(C);
        Console.ReadKey();
    }
}
