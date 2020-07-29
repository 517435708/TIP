package pl.pp.tiplab.securevoipclient.cryptographic;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Caller {
    void sendMessage() throws
                       NoSuchPaddingException,
                       NoSuchAlgorithmException,
                       InvalidKeyException;
    void receiveMessage();
}
