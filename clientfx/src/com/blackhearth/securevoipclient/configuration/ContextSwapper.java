package com.blackhearth.securevoipclient.configuration;

import com.blackhearth.securevoipclient.client.BasicClientData;
import com.blackhearth.securevoipclient.client.connection.ConnectionService;
import com.blackhearth.securevoipclient.client.user.VoIPUser;
import com.blackhearth.securevoipclient.client.waitingroom.WaitingRoomService;
import com.blackhearth.securevoipclient.rsa.Random128bit;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class ContextSwapper {

    private static final double CLIENT_WIDTH = 300.0;
    private static final double CLIENT_HIGH = 600.0;

    private final BasicClientData basicClientData;
    private final WaitingRoomService waitingRoomService;
    private final ConnectionService connectionService;
    private final BasicClientData clientData;
    private final VBox scene;

    @Autowired
    @Qualifier("callingUsers")
    private List<Pair<String, String>> callingUsers;

    @Resource(name = "sendingData")
    private BlockingQueue<byte[]> sendingData;

    @Autowired
    @Qualifier("waitingUsers")
    private List<Button> waitingUsers;


    public void swapToWaitingRoom() {
        generateUsers(waitingRoomService.getUsers(), scene);
    }

    private void generateUsers(List<VoIPUser> users, VBox scene) {
        Platform.runLater(() -> {
            scene.getChildren()
                 .clear();
            waitingUsers.clear();
            for (VoIPUser user : users) {
                if (!user.getNick()
                         .equals(basicClientData.getNickName())) {
                    Button button = new Button();
                    button.setMinWidth(CLIENT_WIDTH);
                    button.wrapTextProperty()
                          .setValue(true);
                    button.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"),
                                                                           null,
                                                                           null)));
                    button.setOnMouseClicked(event -> {
                        for (var pair : callingUsers) {
                            if (pair.getKey()
                                    .equals(button.getText())) {
                                connectionService.acceptConnection(clientData.getUserToken(), pair.getValue());
                                swapToCallingActivity(pair.getKey());
                                return;
                            }
                        }
                        var response = connectionService.tryConnectWith(clientData.getUserToken(), button.getText());
                        if (response.getMessage().equals("OK")) {
                            Random128bit random128bit = new Random128bit();
                            String message = "ACCEPT" + random128bit.getResult();
                            try {
                                sendingData.put(message.getBytes());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            swapToCallingActivity(button.getText());
                        }
                    });
                    button.setText(user.getNick());
                    waitingUsers.add(button);
                    scene.getChildren()
                         .add(button);
                }
            }
        });
    }

    private void swapToCallingActivity(String key) {
        Platform.runLater(() -> {
            for (var pair : callingUsers) {
                if (!pair.getKey()
                         .equals(key)) {
                    connectionService.refuseConnection(clientData.getUserToken(), pair.getValue());
                }
            }
            basicClientData.setCompanion(key);
            callingUsers.clear();
            scene.getChildren()
                 .clear();
            waitingUsers.clear();
            Text text = new Text("Currently Talking with: " + key);
            Button disconnect = new Button();
            disconnect.setText("Disconnect");
            disconnect.setMinWidth(CLIENT_WIDTH);
            disconnect.setOnMouseClicked(event -> {
                try {
                    sendingData.put("REJECT".getBytes());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                swapToWaitingRoom();
            });
            scene.getChildren()
                 .add(text);
            scene.getChildren()
                 .add(disconnect);
        });
    }


}
