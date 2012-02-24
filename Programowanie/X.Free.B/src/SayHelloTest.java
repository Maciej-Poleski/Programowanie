import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
* User: evil
* Date: 24.02.12
* Time: 22:09
*/
public class SayHelloTest {
    @Test
    public void testToString() throws Exception {
        assertEquals(new SayHello().toString(),"Hello. I'm Max.");
    }
}
