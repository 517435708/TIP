package pl.pp.tiplab.securevoipclient.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RsaCoverter {
    PrivateKey privateKeyFromString(String privateKey);
    String stringFromPrivateKey(PrivateKey privateKey);

    String stringFromPublicKey(PublicKey publicKey);
    PublicKey publicKeyFromString(String publicKey);
}
