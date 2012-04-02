/**
 * User: Maciej Poleski
 * Date: 24.03.12
 * Time: 12:49
 */
public class Test0 {
    public static class StrRvs implements Function<String, String>{
        private final String[] args = new String[1];
        @Override
        public String[] in() { return args; }
        @Override
        public String out() { return new StringBuilder(args[0]).reverse().toString(); }
    }
    public static void main(String[] args) throws GenericFunctionsException {
        Function<String,String> f = new StrRvs();
        f.in()[0] = "Ala";
        System.out.println(f.out());
        f = Functions.compose(f, f);
        f.in()[0] = "Ala";
        System.out.println(f.out());
        f = Functions.compose(new StrRvs(), f);
        f.in()[0] = "Ala";
        System.out.println(f.out());
    }
}