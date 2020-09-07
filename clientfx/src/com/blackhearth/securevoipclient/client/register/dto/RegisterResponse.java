package com.blackhearth.securevoipclient.client.register.dto;

import com.blackhearth.securevoipclient.client.user.VoIPUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {
    private String nick;
    private String userToken;
    private List<VoIPUser> users;
}
