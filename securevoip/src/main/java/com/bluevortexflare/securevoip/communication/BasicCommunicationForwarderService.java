package com.bluevortexflare.securevoip.communication;

import com.bluevortexflare.securevoip.session.UserSessionService;
import com.bluevortexflare.securevoip.users.register.VoIPUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicCommunicationForwarderService implements CommunicationForwarderService {


    private final DatagramSocket socket;
    private final UserSessionService sessionService;

    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;

    private byte[] message = new byte[1616];

    @Override
    public void run() {
        new Thread(() -> {
            while (true) {
                Arrays.fill(message, (byte)0);
                DatagramPacket datagram = receiveDatagram();
                System.out.println(new String(datagram.getData()));
                if (!setUserInetAddressIfTokenReceived(datagram)) {
                    Optional<InetAddress> address = sessionService.getOppositeAddressFromSession(datagram);
                    address.ifPresent(this::forwardDatagram);
                }
            }
        }).start();
    }


    @Override
    public void makeCall(String initiatorsToken,
                         String responderNick,
                         String requestId) {

        for (var user : users) {
            if (user.getNick().contains(responderNick)) {
                String callMessage = "CALLING" + getInitiatorNickFromToken(initiatorsToken) +
                        "|" +
                        requestId;
                sendMessageToUser(callMessage.getBytes(), user);
                return;
            }
        }

    }

    @Override
    public void sendRefuseMessage(String initiatorsToken) {
        for (var user : users) {
            if (user.getUserToken().equals(initiatorsToken)) {
                sendMessageToUser("REJECTED".getBytes(), user);
                return;
            }
        }
    }

    private String getInitiatorNickFromToken(String initiatorsToken) {
        for (var user : users) {
            if (user.getUserToken().equals(initiatorsToken)) {
                return user.getNick();
            }
        }

        return "";
    }

    private int PORT = 12345;

    private void sendMessageToUser(byte[] message, VoIPUser user) {
        DatagramPacket packet = new DatagramPacket(message, message.length, user.getAddressIp(), PORT);
        try {
            socket.send(packet);
        } catch (IOException e) {
            log.error(e.toString() + "\ncause: " + e.getCause()
                                                    .toString());
        }
    }

    private void sendMessageToAllUsers(byte[] message, VoIPUser userExcluded) {
        for (var user : users) {
            if (user.isReadyToTalk() && user != userExcluded) {
                DatagramPacket packet = new DatagramPacket(message, message.length, user.getAddressIp(), PORT);
                try {
                    socket.
                                  send(packet);
                } catch (IOException e) {
                    log.error(e.toString() + "\ncause: " + e.getCause()
                                                            .toString());
                }
            }
        }
    }

    private boolean setUserInetAddressIfTokenReceived(DatagramPacket datagram) {
        String datagramData = new String(datagram.getData()).trim();
        for (var user : users) {
            if (user.getUserToken().equals(datagramData)) {
                user.setAddressIp(datagram.getAddress());
                user.setReadyToTalk(true);
                sendMessageToAllUsers("NOTIFY".getBytes(), user);
                return true;
            }
        }
        return false;
    }

    private void forwardDatagram(InetAddress address) {
        DatagramPacket packet = new DatagramPacket(message, message.length, address, 12345);
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
