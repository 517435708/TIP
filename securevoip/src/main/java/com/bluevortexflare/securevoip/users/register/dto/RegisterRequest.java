package com.bluevortexflare.securevoip.users.register.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String nick;
    private String host;
    private String publicKey;
}
