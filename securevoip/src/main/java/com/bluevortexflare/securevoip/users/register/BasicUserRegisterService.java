package com.bluevortexflare.securevoip.users.register;

import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BasicUserRegisterService implements UserRegisterService {
    private AtomicInteger seqNumber = new AtomicInteger();
    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {

        String newNick = generateNickFromRequest(request.getNick());
        String userToken = UUID.randomUUID()
                               .toString();
        String publicKey = request.getPublicKey();
        return addToWaitingRoom(newNick, userToken, publicKey);
    }

    private RegisterResponse addToWaitingRoom(String newNick, String userToken, String publicKey) {
        VoIPUser user = new VoIPUser();
        user.setNick(newNick);
        user.setUserToken(userToken);
        user.setReadyToTalk(true);
        user.setPublicKey(publicKey);
        users.add(user);
        return new RegisterResponse(newNick, userToken);
    }

    private String generateNickFromRequest(String nick) {
        String s = nick + " " + seqNumber;
        seqNumber.getAndIncrement();
        return s;
    }
}
