/**
 * User: Maciej Poleski
 * Date: 02.04.12
 * Time: 13:33
 */
public class ExceptionsAndInheritance {
    static class MyExceptionTier1 extends Exception {};
    static class MyExceptionTier2 extends MyExceptionTier1 {};
    static interface MyInterface {
        void myMethod() throws MyExceptionTier1;
    }
    static class MyClassEx1 implements MyInterface {
        MyClassEx1() throws MyExceptionTier2 {}
        public void myMethod() throws MyExceptionTier2 {}
        //public void myMethod() {} OK
        //public void myMethod() throws MyExceptionTier1 {} still OK
        //public void myMethod() throws Exception {} //compile-time error
    }
    static class MyClassEx2 extends MyClassEx1 {
        MyClassEx2() throws Exception, MyExceptionTier2 {}
        public void myMethod() throws MyExceptionTier2 {}
        //public void myMethod() throws MyExceptionTier1 {} compile-time error
        //public void myMethod() throws Exception {} compile-time error
        public void newMethod() throws Exception {}
    }
}