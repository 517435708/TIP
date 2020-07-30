package pl.pp.tiplab.securevoipclient.rsa;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Converter implements RsaCoverter {

    @Override
    public PrivateKey privateKeyFromString(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    @Override
    public String stringFromPrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder()
                .encodeToString(privateKey.getEncoded());
    }

    @Override
    public String stringFromPublicKey(PublicKey publicKey) {
        return Base64.getEncoder()
                .encodeToString(publicKey.getEncoded());
    }

    @Override
    public PublicKey publicKeyFromString(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] publicBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
