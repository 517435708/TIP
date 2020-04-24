package com.bluevortexflare.securevoip.communication;

import com.bluevortexflare.securevoip.session.UserSessionService;
import com.bluevortexflare.securevoip.users.register.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Optional;

@Slf4j
@Service
public class BasicCommunicationForwarderService implements CommunicationForwarderService {


    private DatagramSocket socket;
    private UserSessionService sessionService;

    private byte[] message = new byte[1024];

    public BasicCommunicationForwarderService(DatagramSocket socket,
                                              UserSessionService sessionService) {
        this.socket = socket;
        this.sessionService = sessionService;
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (true) {
                DatagramPacket datagram = receiveDatagram();
                Optional<InetAddress> address = sessionService.getAddressFromSession(datagram);
                address.ifPresent(this::forwardDatagram);
            }
        }).start();
    }

    @Override
    public void makeCall(String initiatorsToken,
                         String respondersToken,
                         String requestId) {

    }

    @Override
    public void sendRefuseMessage(String initiatorsToken) {

    }

    private void forwardDatagram(InetAddress address) {
        DatagramPacket packet = new DatagramPacket(message, message.length, address, 1337);
        try {
            socket.send(packet);
        } catch (IOException e) {
            log.error(e.toString() + "\ncause: " + e.getCause()
                                                    .toString());
        }
    }

    private DatagramPacket receiveDatagram() {
        DatagramPacket datagram = new DatagramPacket(message, message.length);
        try {
            socket.receive(datagram);
        } catch (IOException e) {
            log.error(e.toString() + "\ncause: " + e.getCause()
                                                    .toString());
        }
        return datagram;
    }


}
