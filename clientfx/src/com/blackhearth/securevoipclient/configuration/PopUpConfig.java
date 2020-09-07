package com.blackhearth.securevoipclient.configuration;

import com.blackhearth.securevoipclient.client.BasicClientData;
import com.blackhearth.securevoipclient.client.register.RegisterService;
import com.blackhearth.securevoipclient.client.register.dto.RegisterRequest;
import com.blackhearth.securevoipclient.rsa.BasicConverter;
import com.blackhearth.securevoipclient.rsa.RSAGenerator;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

@Configuration
@RequiredArgsConstructor
public class PopUpConfig {


    private static final double CLIENT_WIDTH = 300.0;
    private static final double CLIENT_HIGH = 600.0;

    private final RegisterService registerService;
    private final RSAGenerator rsaGenerator;
    private final BasicConverter converter;
    private final BasicClientData basicClientData;
    private final ContextSwapper contextSwapper;
    private final VBox scene;

    @Resource(name = "sendingData")
    public BlockingQueue<byte[]> sending;

    @PostConstruct
    public void popup() {
        Platform.runLater(() -> {

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);


            TextField textArea = new TextField();
            textArea.setPrefHeight(15);
            textArea.setMinWidth(100);

            Button button = new Button();
            button.setMinWidth(100);
            button.setText("Register");
            button.setOnMouseClicked(event -> {
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setNick(textArea.getText());
                basicClientData.setPrivateKey(rsaGenerator.getPrivateKey());
                basicClientData.setPublicKey(rsaGenerator.getPublicKey());
                registerRequest.setPublicKey(converter.stringFromPublicKey(rsaGenerator.getPublicKey()));
                var response = registerService.registerUser(registerRequest);
                basicClientData.setNickName(response.getNick());
                basicClientData.setUserToken(response.getUserToken());
                sending.add(response.getUserToken()
                                    .getBytes());
                contextSwapper.swapToWaitingRoom();
                dialog.close();
                scene.toFront();
            });

            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren()
                      .add(new Text("Login"));
            dialogVbox.getChildren()
                      .add(textArea);
            dialogVbox.getChildren()
                      .add(button);

            Scene dialogScene = new Scene(dialogVbox, 100, 200);
            dialog.setScene(dialogScene);
            dialog.show();

        });
    }

}
