package pl.pp.tiplab.securevoipclient.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;

public class MockedRsaConverter implements RsaCoverter {
    @Override
    public PrivateKey privateKeyFromString(String privateKey) {
        return new PrivateKey() {
            @Override
            public String getAlgorithm() {
                return null;
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
        };
    }

    @Override
    public String stringFromPrivateKey(PrivateKey privateKey) {
        return "null";
    }

    @Override
    public String stringFromPublicKey(PublicKey publicKey) {
        return "null";
    }

    @Override
    public PublicKey publicKeyFromString(String publicKey) {
        return new PublicKey() {
            @Override
            public String getAlgorithm() {
                return null;
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
        };
    }
}
