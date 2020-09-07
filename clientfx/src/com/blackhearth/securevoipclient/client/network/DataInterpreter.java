package com.blackhearth.securevoipclient.client.network;

import com.blackhearth.securevoipclient.configuration.ContextSwapper;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInterpreter {

    private final ContextSwapper contextSwapper;

    @Autowired
    @Qualifier("callingUsers")
    private List<Pair<String, String>> callingUsers;


    @Autowired
    @Qualifier("waitingUsers")
    private List<Button> waitingUsers;

    void proceed(byte[] data) {
        String message = new String(data);
        message = message.trim();
        // Najbardziej gówniany kod jaki w życiu napisałem. Kill me....
        if ("NOTIFY".equals(message)) {
            contextSwapper.swapToWaitingRoom();
        } else if ("REJECT".equals(message)) {
       //     rejectTheCall();
        } else if (message.contains("CALLING")) {
            addCallingUser(message);
        } else if (message.contains("ACCEPT")) {
           // swapToCallingContext(talker.getNick());
           // tradeKeys(message.substring(6));
        } else if (message.contains("AES")) {
           // acceptKey(message);
        //} else if (speaker != null) {
            //sendToSpeaker(message);
        } else {
           // return false;
        }
        //return true;
    }

    private void addCallingUser(String message) {
        message = message.substring(8);
        String[] data =message.split("\\|");
        callingUsers.add(new Pair<>(data[0], data[1]));

        for (var user : waitingUsers) {
            if (user.getText().equals(data[0])) {
                user.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00FF00"), null, null)));
            }
        }

    }


}
