package com.blackhearth.securevoipclient.configuration;

import javafx.scene.control.Button;
import javafx.util.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "callingUsers")
    public List<Pair<String, String>> callingUsers() {
        return new ArrayList<>();
    }

    @Bean(name = "waitingUsers")
    public List<Button> waitingUsers() {
        return new ArrayList<>();
    }
}
