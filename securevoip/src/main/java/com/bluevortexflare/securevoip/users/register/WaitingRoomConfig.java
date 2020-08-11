package com.bluevortexflare.securevoip.users.register;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class WaitingRoomConfig {


    @Bean(name = "waitingRoom")
    public List<VoIPUser> waitingRoom() {
        return new CopyOnWriteArrayList<>();
    }

}
