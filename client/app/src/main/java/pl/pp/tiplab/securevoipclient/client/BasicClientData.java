package pl.pp.tiplab.securevoipclient.client;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicClientData {
    private String nickName;
    private String userToken;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String AESKey;
}
