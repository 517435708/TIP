package com.blackhearth.securevoipclient.client.network;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;

import static com.blackhearth.securevoipclient.ApplicationConstants.APPLICATION_HOST;
import static com.blackhearth.securevoipclient.ApplicationConstants.APPLICATION_PORT;


@Component
public class ClientSender {

    private static final int SENDER_CLIENT_PORT = 12346;

    public void put(byte[] data) {
        try (DatagramSocket udpSocket = new DatagramSocket(SENDER_CLIENT_PORT)) {
            InetAddress serverAddress = InetAddress.getByName(APPLICATION_HOST);
            sendMessageIfNotNull(data, serverAddress, udpSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessageIfNotNull(byte[] message,
                                      InetAddress serverAddress,
                                      DatagramSocket udpSocket) throws
                                                                IOException {
        if (message != null) {
            DatagramPacket packet = new DatagramPacket(message,
                                                       message.length,
                                                       serverAddress,
                                                       APPLICATION_PORT);
            udpSocket.send(packet);
        }
    }
}
