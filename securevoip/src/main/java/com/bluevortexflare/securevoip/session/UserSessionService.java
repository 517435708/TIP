package com.bluevortexflare.securevoip.session;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Optional;

public interface UserSessionService {
    Optional<InetAddress> getAddressFromSession(DatagramPacket datagram);

    String createNewSession(String initiatorsToken);

    String killSession(String userToken, String sessionIdToken);

    void addUserToSession(String respondersSessionToken, String sessionIdToken);

}
