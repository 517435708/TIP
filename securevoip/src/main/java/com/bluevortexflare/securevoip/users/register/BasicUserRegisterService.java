package com.bluevortexflare.securevoip.users.register;

import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasicUserRegisterService implements UserRegisterService {

    private List<VoIPUser> users = new ArrayList<>();

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {

        String newNick = generateNickFromRequest(request.getNick());
        String userToken = UUID.randomUUID()
                               .toString();

        return addToWaitingRoom(newNick, userToken);
    }

    private RegisterResponse addToWaitingRoom(String newNick, String sessionToken) {

        return new RegisterResponse(newNick, sessionToken);
    }

    private String generateNickFromRequest(String nick) {
        return null;
    }
}
