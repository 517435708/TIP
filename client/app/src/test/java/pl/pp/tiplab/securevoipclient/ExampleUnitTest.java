package pl.pp.tiplab.securevoipclient;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import pl.pp.tiplab.securevoipclient.RSA.Random128bit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Random128bit random = new Random128bit();

    @Test
    public void is32letters() {
        assertTrue(random.getResult().length() == 32);
    }

    @Test
    public void isHexadecimal() {
        String hexDigits = "abcdef0123456789";
        assertTrue(StringUtils.containsOnly(random.getResult(), hexDigits));

    }

}