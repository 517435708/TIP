package com.bluevortexflare.securevoip.session;

import com.bluevortexflare.securevoip.exceptions.VoiPRuntimeException;
import com.bluevortexflare.securevoip.users.register.VoIPUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasicUserSessionService implements UserSessionService {

    private List<VoIPSession> sessions = new ArrayList<>();
    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;


    @Override
    public Optional<InetAddress> getOppositeAddressFromSession(DatagramPacket datagram) {
        for (var session : sessions) {
            if (datagram.getAddress()
                        .equals(session.getInitiator()
                                       .getAddressIp())) {
                return Optional.of(session.getResponder()
                                          .getAddressIp());
            } else if (datagram.getAddress()
                               .equals(session.getResponder()
                                              .getAddressIp())) {
                return Optional.of(session.getInitiator()
                                          .getAddressIp());
            }
        }

        return Optional.empty();
    }

    @Override
    public String createNewSession(String initiatorsToken) {
        VoIPSession session = new VoIPSession();
        VoIPUser user = users.stream()
                             .filter(u -> u.getUserToken()
                                           .equals(initiatorsToken))
                             .findFirst()
                             .orElseThrow(() -> new VoiPRuntimeException(404));

        session.setInitiator(user);
        session.setSessionId(UUID.randomUUID()
                                 .toString());

        return session.getSessionId();
    }

    @Override
    public String killSession(String userToken, String sessionIdToken) {

        VoIPSession session = sessions.stream()
                                      .filter(s -> s.getSessionId()
                                                    .equals(sessionIdToken))
                                      .findFirst()
                                      .orElseThrow(() -> new VoiPRuntimeException(404));


        if (session.getInitiator()
                   .getUserToken()
                   .equals(userToken)) {
            sessions.remove(session);
            return session.getResponder()
                          .getUserToken();
        } else if (session.getResponder()
                          .getUserToken()
                          .equals(userToken)) {
            sessions.remove(session);
            return session.getInitiator()
                          .getUserToken();
        } else {
            throw new VoiPRuntimeException(400);
        }
    }

    @Override
    public void addUserToSession(String respondersSessionToken, String sessionIdToken) {

        VoIPUser user = users.stream()
                             .filter(u -> u.getUserToken()
                                           .equals(respondersSessionToken))
                             .findFirst()
                             .orElseThrow(() -> new VoiPRuntimeException(404));
        VoIPSession session = sessions.stream()
                                      .filter(s -> s.getSessionId()
                                                    .equals(sessionIdToken))
                                      .findFirst()
                                      .orElseThrow(() -> new VoiPRuntimeException(404));
        session.setResponder(user);

    }
}
