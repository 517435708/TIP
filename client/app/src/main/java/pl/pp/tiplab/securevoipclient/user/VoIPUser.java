package pl.pp.tiplab.securevoipclient.user;


import java.net.InetAddress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoIPUser {

    private int id;
    private String nick;
    private String publicKey;
    private String userToken;
    private InetAddress addressIp = null;
    private boolean readyToTalk = true;

}

