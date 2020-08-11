package com.bluevortexflare.securevoip.users.register.dto;

import com.bluevortexflare.securevoip.users.register.VoIPUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String nick;
    private String userToken;
    private List<VoIPUser> users;
}
