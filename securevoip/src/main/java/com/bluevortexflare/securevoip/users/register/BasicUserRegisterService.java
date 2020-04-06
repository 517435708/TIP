package com.bluevortexflare.securevoip.users.register;

import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public class BasicUserRegisterService implements UserRegisterService {
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        return null;
    }
}
