using System;
using System.IO;
using System.Xml.Serialization;

public class Test3A
{
    public Unique<Test3A> self;
}
public class Test3
{
    public static void Test(params string[] args)
    {
        Test3A a = new Test3A();
        a.self = a;
        StringWriter sW = new StringWriter();
        XmlSerializer serializer = new XmlSerializer(typeof(Test3A));
        serializer.Serialize(sW, a);
        StringReader sR = new StringReader(sW.ToString());
        Test3A des = (Test3A)serializer.Deserialize(sR);
        Console.WriteLine(object.ReferenceEquals(a, des));
        Console.WriteLine(object.ReferenceEquals(des, des.self.Content));
        Console.WriteLine(object.ReferenceEquals(des.self.Content, des.self.Content.self.Content));
    }
}