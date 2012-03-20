import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * User: Maciej Poleski
 * Date: 17.03.12
 * Time: 20:24
 */

interface MyInterface1 {
    void method1(Integer a) throws Exception;
}

class TestClass1 {
    public void method1(Number a) throws InvalidArgumentException {
        System.out.println("method1 " + a);
//        throw new InvalidArgumentException(new String[] {});
//        return a;
    }
}

public class Test1 {
    public static void main(String... args) throws Exception {
        Integer i = 5;
        Comparable<Integer> cmp = SmartFactory.fixIt(Comparable.class, i);
        System.out.println(cmp.compareTo(5));
        System.out.println(cmp.compareTo(4));
        System.out.println(cmp.compareTo(6));

        MyInterface1 int1 = SmartFactory.fixIt(MyInterface1.class, i);
        //int1.method1();
        System.out.println(int1.toString());

        MyInterface1 int2 = SmartFactory.fixIt(MyInterface1.class, new TestClass1());
        int2.method1(5);
        System.out.println(int2.toString());
    }
}
