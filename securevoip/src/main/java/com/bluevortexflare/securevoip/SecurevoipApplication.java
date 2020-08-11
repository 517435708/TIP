package com.bluevortexflare.securevoip;

import com.bluevortexflare.securevoip.communication.CommunicationForwarderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SecurevoipApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SecurevoipApplication.class, args);
		CommunicationForwarderService communicationForwarderService = context.getBean("basicCommunicationForwarderService", CommunicationForwarderService.class);
		communicationForwarderService.run();
	}

}
