package com.bluevortexflare.securevoip.communication;

import java.net.DatagramSocket;
import java.net.InetAddress;

public interface CommunicationForwarderService {

    void run();

    void makeCall(String initiatorsToken,
                  String respondersToken,
                  String requestId);

    void sendRefuseMessage(String initiatorsToken);
}
