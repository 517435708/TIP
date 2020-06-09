package pl.pp.tiplab.securevoipclient.client.register.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nick;
    private String host;
    private String publicKey;
}
