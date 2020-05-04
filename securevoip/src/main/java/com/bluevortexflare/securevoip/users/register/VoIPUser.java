package com.bluevortexflare.securevoip.users.register;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;


@Getter
@Setter
public class VoIPUser {

    private int id;

    private String nick;

    private String publicKey;

    private String userToken;

    private InetAddress addressIp = null;

    private boolean readyToTalk = true;


}
