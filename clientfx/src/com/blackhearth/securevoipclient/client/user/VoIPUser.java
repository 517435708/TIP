package com.blackhearth.securevoipclient.client.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.InetAddress;

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

