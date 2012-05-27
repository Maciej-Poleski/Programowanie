using System;
using System.IO;
using System.Xml.Serialization;


public class Test1SillyUnique<T>
{
    [XmlElement(IsNullable = true)]
    public T Content { get; set; }
}

public class Test1A
{
    public Test1SillyUnique<string> aString = new Test1SillyUnique<string>();
    public override string ToString()
    {
        return "Test1A: " + aString.Content;
    }
}

public class Test1B
{
    public Unique<string> aString = new Unique<string>();
    public override string ToString()
    {
        return "Test1A: " + aString.Content;
    }
}

public class Test1
{
    public static void Test(params string[] args)
    {
        Test1A a = new Test1A();
        a.aString.Content = "Ala ma kota";
        StringWriter sW = new StringWriter();
        XmlSerializer serializerForA = new XmlSerializer(typeof(Test1A));
        serializerForA.Serialize(sW, a);
        Console.WriteLine(sW);

        StringReader sR = new StringReader(sW.ToString());
        Test1A desA = (Test1A)serializerForA.Deserialize(sR);
        Console.WriteLine(a);
        Console.WriteLine(desA);

        sR = new StringReader(sW.ToString());
        XmlRootAttribute root = new XmlRootAttribute();
        root.ElementName = "Test1A";
        XmlSerializer serializerForB = new XmlSerializer(typeof(Test1B), root);
        Test1B desB = (Test1B)serializerForB.Deserialize(sR);
        Console.WriteLine(a);
        Console.WriteLine(desB);

        Test1B b = new Test1B();
        b.aString.Content = "Ala ma kota";
        sW = new StringWriter();
        serializerForB.Serialize(sW, b);
        sR = new StringReader(sW.ToString());
        desA = (Test1A)serializerForA.Deserialize(sR);
        Console.WriteLine(a);
        Console.WriteLine(desA);
    }
}

