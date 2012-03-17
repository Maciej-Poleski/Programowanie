import org.testng.annotations.*;

/**
 * User: Maciej Poleski
 * Date: 15.03.12
 * Time: 17:40
 */
public class FreakListBypassAccessDeniedTest {

    @Test(expectedExceptions = WasWrong.class)
    public void testPop() throws Throwable {
        FreakList<Integer> freakList = new FreakList<Integer>() {
            @Override
            public Integer pop() throws AccessDenied, WasWrong, IsEmpty {
                throw new WasWrong();
            }
        };
        FreakListBypassAccessDenied<Integer> list = new FreakListBypassAccessDenied(freakList);
        list.pop();
    }
}
