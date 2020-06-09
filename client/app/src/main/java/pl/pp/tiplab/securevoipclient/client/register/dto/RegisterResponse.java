package pl.pp.tiplab.securevoipclient.client.register.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String nick;
    private String sessionId;
}
