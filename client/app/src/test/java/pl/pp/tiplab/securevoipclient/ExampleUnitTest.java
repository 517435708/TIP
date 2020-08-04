package pl.pp.tiplab.securevoipclient;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import pl.pp.tiplab.securevoipclient.rsa.BasicConverter;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;
import pl.pp.tiplab.securevoipclient.rsa.Random128bit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Random128bit random = new Random128bit();
    BasicConverter basicConverter = new BasicConverter();
    RSAGenerator rsaGenerator = new RSAGenerator();
    PublicKey publicKey = rsaGenerator.getPublicKey();
    PrivateKey privateKey = rsaGenerator.getPrivateKey();
    String publicString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK4CBKQWBy1DcnHnepUVrMHd0mFXS9omdtxHXTeoK/ekoD5pLYdtxw1nhFkNd5hlTnNFk1qXktXQ46LipK3upm4GW9ifTmH8ScdfBUcVy+687ngsHNWQu0GIsWU/HZGtMM7v5ZLnwpiQ2nb5BlRrlcxpHRAl908nnTNhsonxrkxQIDAQAB";
    String privateString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMoT15MGzoBvKaZdTm31+gryhnlPMOaKoKLnDBMr9dnjKLhX9XeX7yJMRsU+TpggFoYZvIRxNLOPx2AI+uVnCmgkuIrcKVlWZ3P2c6AqvPDyFXtvEIYHHmFqEXci8+/GBYytTQ1n4OlebQYanZr3aPaiA7x12Jzng/XaiqJWji+rAgMBAAECgYEAiWDX64JzHOl1vT4ttZ/F729VcF8f9XtFHbpJgTcfmpNN+ZUCsd0+m2iTrEBhmhYmeeBBQyDbHT+f4W/5cb+NWG0SiWatFpMDLlUBz6mewkZtH0PRcAEeAyEIl5NwI6dMrYiN13Xh95NHwz3PlaMJbfqA1mbWqzlXqAqrEUou3KECQQD5wU/2gVtIp3SgGwPfZ9Ag9OqoMzcsA/OsMBkm2mt2ddnIAO6vUnqES7lOF6MuF5aDr0S0Is44J1owLGSSa/vTAkEAzyFYJd5uyVR5PWiLrjo6DqsoKaeEO0P1Sp7WnNjKGlpcdbGdR8HlVeYyALa4AQjtFlO6JVwdT5uqmlHC9X1NyQJATM8Jw3q20xpEwLRuNXecx3Xq8L2Hey7f8pV8Nrm3kN/fkSXQQfTwnt0W6RBV6+fvLyJo06lcmGJp1yTf7TPuZwJAR2LrmWmW7yaA/owYj/ybY9DTb7+/UBm04Xnq53XD+d8ovJtxZHjMDJXgp+RReHHWHHrQze14YKZ63uLM+YStGQJBAKI6OIH7lhH6CBm0J+zCFM7ZDH7UYwlTfomikG0C3hVKyLZDyYnOpklqF8WN5v/Sv7SUuascw85B1ymLOfijrZo=";
    @Test
    public void is32letters() {
        assertTrue(random.getResult().length() == 32);
    }

    @Test
    public void isHexadecimal() {
        String hexDigits = "abcdef0123456789";
        assertTrue(StringUtils.containsOnly(random.getResult(), hexDigits));

    }
    @Test
    public void arePrivateKeysEqual()
    {

        String x = basicConverter.stringFromPrivateKey(privateKey);
        PrivateKey x2 = basicConverter.privateKeyFromString(x);
        PrivateKey z = basicConverter.privateKeyFromString(privateString);
        String z2 = basicConverter.stringFromPrivateKey(z);
        assertEquals(privateKey,x2);
        assertEquals(z2,privateString);
    }
    @Test
    public void arePublicKeysEqual()
    {
        String x = basicConverter.stringFromPublicKey(publicKey);
        PublicKey x2 = basicConverter.publicKeyFromString(x);

        PublicKey y = basicConverter.publicKeyFromString(publicString);
        String y2 = basicConverter.stringFromPublicKey(y);
        assertEquals(publicKey,x2);
        assertEquals(y2,publicString);
    }

}