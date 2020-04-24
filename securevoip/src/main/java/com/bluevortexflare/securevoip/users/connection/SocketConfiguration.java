package com.bluevortexflare.securevoip.users.connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.DatagramSocket;
import java.net.SocketException;

@Configuration
public class SocketConfiguration {

    @Bean
    DatagramSocket socket() throws
                            SocketException {
        return new DatagramSocket(2137);
    }
}
