package com.blackhearth.securevoipclient.client.register;

import com.blackhearth.securevoipclient.client.BasicClientData;
import com.blackhearth.securevoipclient.client.register.dto.RegisterRequest;
import com.blackhearth.securevoipclient.client.register.dto.RegisterResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.blackhearth.securevoipclient.ApplicationConstants.APPLICATION_ENDPOINT;
import static com.blackhearth.securevoipclient.client.register.RegisterConstants.REGISTER_ENDPOINT;

@Setter
@Getter
@Service
@RequiredArgsConstructor
public class BasicClientRegister implements RegisterService {


    private final BasicClientData data;
    private final RestTemplate restTemplate;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response = restTemplate.postForObject(APPLICATION_ENDPOINT + REGISTER_ENDPOINT, request, RegisterResponse.class);
        data.setNickName(Objects.requireNonNull(response).getNick());
        data.setUserToken(response.getUserToken());
        return response;
    }

}
