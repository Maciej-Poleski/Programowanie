public class AddTest
{
    public static void Main()
    {
        RecurrenceFunction add = CreateRecurrence.primitiveRecurrence(
          CreateRecurrence.projection(0),
          CreateRecurrence.composition(
            CreateRecurrence.projection(2),
            CreateRecurrence.successor()));
        System.Console.WriteLine(add(2, 3));
        System.Console.WriteLine(add(20, 33));
        System.Console.ReadKey();
    }
}