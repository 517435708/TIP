package pl.pp.tiplab.securevoipclient.client.register;

import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;

public interface ClientRegister {
    boolean isUserRegistered();
    RegisterResponse registerUser(RegisterRequest request);
}
