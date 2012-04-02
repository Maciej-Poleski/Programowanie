/**
 * User: Maciej Poleski
 * Date: 24.03.12
 * Time: 12:50
 */
public class Test3 {
    public static class StrRvs implements Function<String, String>{
        private final String[] args = new String[1];
        @Override
        public String[] in() { return args; }
        @Override
        public String out() { return new StringBuilder(args[0]).reverse().toString(); }
    }
    public static class StrLen implements Function<String, Integer>{
        private final String[] args = new String[1];
        @Override
        public String[] in() { return args; }
        @Override
        public Integer out() { return args[0].length(); }
    }
    public static class ObjMerge implements Function<Object, Object>{
        private final Object [] args = new Object[3];
        @Override
        public Object[] in() { return args; }

        @Override
        public Object out() { return "(" + args[0] + ", " + args[1] + ", " + args[2] + ")"; }

    }
    public static void main(String[] args) throws GenericFunctionsException {
        Function<String, Object> f = Functions.compose(new ObjMerge(),
                Functions.compose(new StrRvs(), new StrRvs()),
                new StrRvs(),
                new StrLen());
        f = Functions.identifyCoordinates(f, 2, 1, 0);
        f.in()[0] = "Ala ma kota";
        System.out.println(f.out());
    }
}