package com.bluevortexflare.securevoip.session;

import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasicUserSessionService implements UserSessionService {

    List<VoIPSession> sessions = new ArrayList<>();

    @Override
    public Optional<InetAddress> getAddressFromSession(DatagramPacket datagram) {

        String message = new String(datagram.getData());


        return Optional.empty();
    }

    @Override
    public String createNewSession(String initiatorsToken, String respondersNick) {
        return null;
    }

    @Override
    public String killSession(String userToken, String sessionIdToken) {
        return null;
    }

    @Override
    public void addUserToSession(String respondersSessionToken, String sessionIdToken) {

    }
}
