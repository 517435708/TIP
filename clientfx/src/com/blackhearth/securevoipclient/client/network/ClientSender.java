package com.blackhearth.securevoipclient.client.network;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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
@RequiredArgsConstructor
public class ClientSender {

    private static final int SENDER_CLIENT_PORT = 12346;

    @Resource(name = "sendingData")
    private BlockingQueue<byte[]> sendingData;

    @PostConstruct
    public void run() {
        new Thread(() -> {
            boolean run = true;
            while (run) {
                while (!sendingData.isEmpty() && run) {
                    try (DatagramSocket udpSocket = new DatagramSocket(SENDER_CLIENT_PORT)) {
                        InetAddress serverAddress = InetAddress.getByName(APPLICATION_HOST);
                        byte[] message = sendingData.poll();
                        sendMessageIfNotNull(message, serverAddress, udpSocket);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        run = false;
                    }
                }
            }
        }).start();
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
            System.out.println("SENDING: " + new String(message));
            udpSocket.send(packet);
        }
    }
}
