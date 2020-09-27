package com.blackhearth.securevoipclient.client.register;


import com.blackhearth.securevoipclient.client.register.dto.RegisterRequest;
import com.blackhearth.securevoipclient.client.register.dto.RegisterResponse;

public interface RegisterService {
    RegisterResponse registerUser(RegisterRequest request);
}
