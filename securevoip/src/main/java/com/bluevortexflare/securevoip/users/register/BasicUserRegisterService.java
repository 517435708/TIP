package com.bluevortexflare.securevoip.users.register;

import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class BasicUserRegisterService implements UserRegisterService {
    private int seq_number = 0;
    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {

        String newNick = generateNickFromRequest(request.getNick());
        String userToken = UUID.randomUUID()
                               .toString();
        return addToWaitingRoom(newNick, userToken);
    }

    private RegisterResponse addToWaitingRoom(String newNick, String userToken) {
     VoIPUser user = new VoIPUser();
     user.setNick(newNick);
     user.setUserToken(userToken);
     users.add(user);
     return new RegisterResponse(newNick, userToken);
    }

    private String generateNickFromRequest(String nick) {
        String s = nick + " " + seq_number;
        seq_number++;
        return s;
    }
}
