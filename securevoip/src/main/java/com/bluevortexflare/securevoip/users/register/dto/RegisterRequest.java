package com.bluevortexflare.securevoip.users.register.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nick;
    private String publicKey;
}
