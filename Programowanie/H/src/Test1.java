/**
 * User: Maciej Poleski
 * Date: 24.03.12
 * Time: 12:50
 */
public class Test1 {
    public static class StrRvs implements Function<String, String>{
        private final String[] args = new String[1];
        @Override
        public String[] in() { return args; }
        @Override
        public String out() { return new StringBuilder(args[0]).reverse().toString(); }
    }

    public static class StrConcat implements Function<String, String>{
        private final String[] args = new String[2];
        @Override
        public String[] in() { return args; }
        @Override
        public String out() { return args[0] + args[1]; }
    }
    public static void main(String[] args) throws GenericFunctionsException {
        Function<String, String> f = Functions.compose(new StrRvs(), new StrRvs());
        f = Functions.compose(new StrConcat(), f, new StrRvs());
        f.in()[0] = f.in()[1]= "Ala";
        System.out.println(f.out());
        f = Functions.compose(new StrConcat(), new StrConcat(), new StrConcat());
        f.in()[0] = f.in()[1] = f.in()[2] = f.in()[3] = "*";
        System.out.println(f.out());
    }
}