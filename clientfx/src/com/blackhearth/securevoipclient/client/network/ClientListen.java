package com.blackhearth.securevoipclient.client.network;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

import static com.blackhearth.securevoipclient.client.network.NetworkConstants.MAX_MESSAGE_RECEIVED;


@Component
@AllArgsConstructor
public class ClientListen {

    static final int LISTEN_CLIENT_PORT = 12345;

    private final DataInterpreter dataInterpreter;

    @PostConstruct
    public void run() {
        new Thread(() -> {
            boolean run = true;
            while (run) {
                try (DatagramSocket udpSocket = new DatagramSocket(LISTEN_CLIENT_PORT)) {
                    byte[] message = new byte[MAX_MESSAGE_RECEIVED];
                    DatagramPacket packet = new DatagramPacket(message, message.length);
                    udpSocket.receive(packet);
                    System.out.println(new String(packet.getData()));
                    dataInterpreter.proceed(packet.getData());
                } catch (IOException e) {
                    run = false;
                }
            }
        }).start();
    }
}
