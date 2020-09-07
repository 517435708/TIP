package com.blackhearth.securevoipclient.configuration;

import com.blackhearth.securevoipclient.client.BasicClientData;
import com.blackhearth.securevoipclient.client.register.RegisterService;
import com.blackhearth.securevoipclient.client.register.dto.RegisterRequest;
import com.blackhearth.securevoipclient.client.user.VoIPUser;
import com.blackhearth.securevoipclient.client.waitingroom.WaitingRoomService;
import com.blackhearth.securevoipclient.rsa.BasicConverter;
import com.blackhearth.securevoipclient.rsa.RSAGenerator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;


@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private static final double CLIENT_WIDTH = 300.0;
    private static final double CLIENT_HIGH = 600.0;


    @Bean(name = "client")
    public Scene gamePanel() {
        VBox gridPane = generateLoginPanel();
        return new Scene(gridPane, CLIENT_WIDTH, CLIENT_HIGH);
    }

    @Bean(name = "scene")
    public VBox generateLoginPanel() {
        return new VBox();
    }

}
