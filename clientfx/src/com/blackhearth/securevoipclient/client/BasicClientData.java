package com.blackhearth.securevoipclient.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
@Component
public class BasicClientData {
    private String nickName;
    private String userToken;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String AESKey;
    private String companion;
}
