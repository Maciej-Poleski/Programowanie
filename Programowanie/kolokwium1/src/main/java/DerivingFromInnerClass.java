/**
 * User: Maciej Poleski
 * Date: 02.04.12
 * Time: 12:27
 */
public class DerivingFromInnerClass {
    final long id = new java.util.Random().nextLong();
    public class Inner {
        String myDescription = "I am the inner class of " + id;
    }
    public class Derived extends Inner {}
//        public static class DerivedStatic extends Inner {}
//    compile-time error
    public static class DerivedStatic extends Inner {
        DerivedStatic (DerivingFromInnerClass outer) {
            outer.super();
            // myDescription = "I am the derived class of inner of " + id;
            // compile-time error
            myDescription += "I am the derived class of inner of " + outer.id;
        }
    }
    public static void main(String[] args) {
        DerivingFromInnerClass tempOne = new DerivingFromInnerClass();
        Inner tempInnerOne = tempOne.new Inner();
        System.out.println(tempInnerOne.myDescription);
        DerivedStatic tempInnerTwo = new DerivedStatic(tempOne);
        System.out.println(tempInnerTwo.myDescription);
        Inner tempInnerThree = tempInnerTwo;
        System.out.println(tempInnerThree.myDescription);
    }
}