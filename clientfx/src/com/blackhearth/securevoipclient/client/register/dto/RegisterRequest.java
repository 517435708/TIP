package com.blackhearth.securevoipclient.client.register.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    private String nick;
    private String publicKey;
}
