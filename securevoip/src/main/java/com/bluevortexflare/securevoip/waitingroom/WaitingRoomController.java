package com.bluevortexflare.securevoip.waitingroom;


import com.bluevortexflare.securevoip.users.register.VoIPUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/waiting-room")
public class WaitingRoomController {

    @Resource(name = "waitingRoom")
    private List<VoIPUser> users;

    @GetMapping
    public List<VoIPUser> getWaitingRoom() {
        return users;
    }

}
