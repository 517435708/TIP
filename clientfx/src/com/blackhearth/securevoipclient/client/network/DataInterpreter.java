package com.blackhearth.securevoipclient.client.network;

import com.blackhearth.securevoipclient.client.BasicClientData;
import com.blackhearth.securevoipclient.configuration.ContextSwapper;
import com.blackhearth.securevoipclient.cryptographic.BasicMicRegister;
import com.blackhearth.securevoipclient.cryptographic.MicRegister;
import com.blackhearth.securevoipclient.rsa.Random128bit;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
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
public class DataInterpreter {

    private final ContextSwapper contextSwapper;
    private final BasicClientData clientData;
    private MicRegister micRegister;

    private Thread chatThread;

    @Resource(name = "sendingData")
    private BlockingQueue<byte[]> sendingData;

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
        if ("REJECT".equals(message)) {
            micRegister.flush();
            micRegister = null;
            contextSwapper.swapToWaitingRoom();
        } else if (micRegister == null) {
            if ("NOTIFY".equals(message)) {
                contextSwapper.swapToWaitingRoom();
            } else if (message.contains("CALLING")) {
                addCallingUser(message);
            } else if (message.contains("ACCEPT")) {
                tradeKeys(message.substring(6));
            } else if (message.contains("AES")) {
                acceptKey(message.substring(3));
            }
        } else {
            micRegister.receiveMessage(data);
        }
    }

    private void acceptKey(String message) {
        clientData.setAESKey(clientData.getAESKey() + message);
        beginChat();
    }

    private void beginChat() {
        micRegister = new BasicMicRegister(clientData.getAESKey());
        chatThread = new Thread(() -> {
            while (micRegister != null) {
                try {
                    sendingData.put(micRegister.sendVoiceMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        chatThread.start();
    }

    private void tradeKeys(String key) {
        String myKey = new Random128bit().getResult();
        clientData.setAESKey(key + myKey);
        try {
            sendingData.put(("AES" + myKey).getBytes());
            beginChat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addCallingUser(String message) {
        message = message.substring(7);
        String[] data = message.split("\\|");
        callingUsers.add(new Pair<>(data[0], data[1]));

        for (var user : waitingUsers) {
            if (user.getText()
                    .equals(data[0])) {
                user.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00FF00"), null, null)));
            }
        }

    }


}
