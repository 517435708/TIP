package com.bluevortexflare.securevoip;


import com.bluevortexflare.securevoip.users.register.VoIPUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DebugController {

    private final DatagramSocket socket;

    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;

    @GetMapping("/debug")
    public void notifyUser() throws
                             UnknownHostException {
        byte[] message = "NOTIFY".getBytes();

        for (var user : users) {
            if (user.getAddressIp() != null) {
                DatagramPacket packet = new DatagramPacket(message, message.length, user.getAddressIp(), 12345);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    log.error(e.toString() + "\ncause: " + e.getCause()
                                                            .toString());
                }
            }
        }


    }
}
