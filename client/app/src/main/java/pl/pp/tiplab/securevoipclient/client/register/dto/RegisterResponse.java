package pl.pp.tiplab.securevoipclient.client.register.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pp.tiplab.securevoipclient.user.VoIPUser;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {
    private String nick;
    private String userToken;
    private List<VoIPUser> users;
}
