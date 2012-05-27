using System;
using System.IO;
using System.Xml.Serialization;

public class Test2A
{
    public string Text { set; get; }
}

public class Test2B
{
    public Unique<Test2A> one;
    public Unique<Test2A> two;
    public Unique<Test2A> three;
}
public class Test2
{
    public static void Test(params string[] args)
    {
        Test2A a1 = new Test2A(), a2 = new Test2A();
        a1.Text = a2.Text = "Ala ma kota";
        Test2B b = new Test2B();
        b.one = new Unique<Test2A>(a1);
        b.two = new Unique<Test2A>(a1);
        b.three = new Unique<Test2A>(a2);
        Console.WriteLine("{0} {1}", object.ReferenceEquals(b.one.Content, b.two.Content),
                          object.ReferenceEquals(b.two.Content, b.three.Content));
        Console.WriteLine("{0} {1} {2}", b.one.Content.Text, b.two.Content.Text, b.three.Content.Text);
        StringWriter sW = new StringWriter();
        XmlSerializer serializer = new XmlSerializer(typeof(Test2B));
        serializer.Serialize(sW, b);
        StringReader sR = new StringReader(sW.ToString());
        b = (Test2B)serializer.Deserialize(sR);
        Console.WriteLine("{0} {1}", object.ReferenceEquals(b.one.Content, b.two.Content),
                          object.ReferenceEquals(b.two.Content, b.three.Content));
        Console.WriteLine("{0} {1} {2}", b.one.Content.Text, b.two.Content.Text, b.three.Content.Text);
    }
}