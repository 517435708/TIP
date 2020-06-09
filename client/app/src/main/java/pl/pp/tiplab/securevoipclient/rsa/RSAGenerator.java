package pl.pp.tiplab.securevoipclient.rsa;

import android.os.Build;

import androidx.annotation.RequiresApi;

import lombok.Getter;
import lombok.Setter;

import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

import javax.crypto.Cipher;

@RequiresApi(api = Build.VERSION_CODES.N)
@Getter
@Setter
public class RSAGenerator {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decryptCipher.doFinal(bytes), UTF_8);
    }
}
