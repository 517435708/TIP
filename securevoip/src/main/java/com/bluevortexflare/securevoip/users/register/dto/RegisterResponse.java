package com.bluevortexflare.securevoip.users.register.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String nick;
    private String sessionId;
}
