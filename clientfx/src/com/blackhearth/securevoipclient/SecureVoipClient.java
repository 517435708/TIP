package com.blackhearth.securevoipclient;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SecureVoipClient extends Application {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new SpringApplicationBuilder(SecureVoipClient.class)
                .web(WebApplicationType.NONE).run(args);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(context.getBean("client", Scene.class));
        stage.show();
        stage.toBack();
    }
}
