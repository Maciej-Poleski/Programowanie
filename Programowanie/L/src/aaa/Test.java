package aaa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * User: Maciej Poleski
 * Date: 19.04.12
 * Time: 14:57
 */
public class Test implements Runnable, Serializable {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}
