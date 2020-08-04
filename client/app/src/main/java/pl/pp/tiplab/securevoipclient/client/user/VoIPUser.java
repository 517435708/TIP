package pl.pp.tiplab.securevoipclient.client.user;


import java.io.Serializable;
import java.net.InetAddress;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoIPUser implements Serializable {
    private int id;
    private String nick;
    private String publicKey;
    private String userToken;
    private InetAddress addressIp = null;
    private boolean readyToTalk = true;
}

