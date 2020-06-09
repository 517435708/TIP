package pl.pp.tiplab.securevoipclient.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicClientData {
    private String nickName;
    private String userToken;
    private String privateKey;
    private String publicKey;
    private String askKey;
}
