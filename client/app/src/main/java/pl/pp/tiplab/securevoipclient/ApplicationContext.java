package pl.pp.tiplab.securevoipclient;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import javax.crypto.Cipher;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.configuration.ButtonOnClickRegister;
import pl.pp.tiplab.securevoipclient.contextswapper.ContextSwapper;
import pl.pp.tiplab.securevoipclient.client.user.VoIPUser;
import pl.pp.tiplab.securevoipclient.cryptographic.BasicMicRegister;
import pl.pp.tiplab.securevoipclient.cryptographic.MicRegister;
import pl.pp.tiplab.securevoipclient.rsa.BasicConverter;
import pl.pp.tiplab.securevoipclient.rsa.Random128bit;

@Getter
@Setter
public class ApplicationContext implements Runnable {

    private List<VoIPUser> users;
    private Map<String, String> callingUsersBySessionToken;
    private BasicClientData data;
    private AppCompatActivity context;
    private ButtonOnClickRegister register;
    private BlockingQueue<byte[]> receivedMessages;
    private BlockingQueue<byte[]> messagesToSend;

    private ContextSwapper swapper;
    private MicRegister speaker;

    private VoIPUser talker;
    private Cipher rsaCipher;

    private StringBuilder aesBuilder;

    private List<Button> buttons;

    private Thread talkingThread;

    @SneakyThrows
    public ApplicationContext(AppCompatActivity context) {
        receivedMessages = new LinkedBlockingQueue<>();
        messagesToSend = new LinkedBlockingQueue<>();
        callingUsersBySessionToken = new HashMap<>();

        rsaCipher = Cipher.getInstance("RSA");
        aesBuilder = new StringBuilder();

        buttons = new ArrayList<>();
        users = new ArrayList<>();
        testForUsers();
        data = new BasicClientData();
        this.context = context;
    }

    public void send(byte[] message) {
        messagesToSend.add(message);
    }

    public void update() {
        swapper.swapToWaitingRoom();
    }

    private void testForUsers() {
        new Thread(() -> {
            while (true) {
                int size = users.size();

                while (size == users.size()) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                context.runOnUiThread(this::updateUserInWaitingRoom);
            }
        });
    }

    @SuppressLint("ResourceType")
    private void updateUserInWaitingRoom() {
        speaker = null;
        LinearLayout layout = context.findViewById(R.id.waiting_room_layout);
        for (VoIPUser user : users) {
            if (!user.getNick()
                     .equals(data.getNickName())) {
                Button button = new Button(context);
                button.setText(user.getNick());
                button.setBackgroundColor(Color.parseColor("#FFFFFF"));
                register.setData(user);
                button.setOnClickListener(register::callButton);
                layout.addView(button);
                buttons.add(button);
            }
        }
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try {
                while (!receivedMessages.isEmpty()) {
                    String message = new String(receivedMessages.poll()).trim();
                    run = examineData(message);
                }
            } catch (Exception ex) {
                run = false;
            }
        }
    }

    private boolean examineData(String message) throws
                                                Exception {

        message = message.trim();
        // Najbardziej gówniany kod jaki w życiu napisałem. Kill me....
        if ("NOTIFY".equals(message)) {
            update();
        } else if ("REJECT".equals(message)) {
            rejectTheCall();
        } else if (message.contains("CALLING")) {
            addCallingUser(message);
        } else if (message.contains("ACCEPT")) {
            swapToCallingContext(talker.getNick());
            tradeKeys(message.substring(6));
        } else if (message.contains("AES")) {
            acceptKey(message);
        } else if (speaker != null) {
            sendToSpeaker(message);
        } else {
            return false;
        }
        return true;
    }

    @SneakyThrows
    private void acceptKey(String message) {
        String key = message.substring(3);
        rsaCipher.init(Cipher.DECRYPT_MODE, data.getPrivateKey());
        aesBuilder.append(new String(rsaCipher.doFinal(key.getBytes())));
        data.setAESKey(aesBuilder.toString());
        start();
    }

    private void addCallingUser(String message) {
        String nick = message.substring(7, message.indexOf("|"));
        String sessionToken = message.substring(message.indexOf("|"));

        callingUsersBySessionToken.put(nick, sessionToken);

        for (Button button : buttons) {
            if (nick.contentEquals(button.getText())) {
                context.runOnUiThread(() -> button.setBackgroundColor(Color.CYAN));
            }
        }

    }

    @SneakyThrows
    private void tradeKeys(String message) {
        String key = new Random128bit().getResult();
        rsaCipher.init(Cipher.DECRYPT_MODE, data.getPrivateKey());
        aesBuilder.append(new String(rsaCipher.doFinal(message.getBytes())));
        aesBuilder.append(key);
        data.setAESKey(aesBuilder.toString());
        rsaCipher.init(Cipher.ENCRYPT_MODE, new BasicConverter().publicKeyFromString(talker.getPublicKey()));
        String aes = "AES" + new String(rsaCipher.doFinal(key.getBytes()));
        send(aes.getBytes());
        start();
    }

    private void start() {
        speaker = new BasicMicRegister(data.getAESKey());
        talkingThread = new Thread(() -> {
            boolean run = true;
            while (run) {
                byte[] data = speaker.sendVoiceMessage();
                send(data);
            }
        });
    }

    private void swapToCallingContext(String nick) {
        speaker = new BasicMicRegister(data.getAESKey());
        for (VoIPUser user : users) {
            if (user.getNick()
                    .equals(nick)) {
                talker = user;
            }
        }
        swapper.swapToCallingRoom();
        context.runOnUiThread(() -> updateNickInCallingRoom(nick));
    }

    private void updateNickInCallingRoom(String nick) {
        TextView nickTextView = context.findViewById(R.id.user_nick_view_id);
        nickTextView.setText(nick);
        Button button = context.findViewById(R.id.end_call_button);
        button.setOnClickListener(register::endCall);
    }

    private void rejectTheCall() {
        //DO NOTHING
    }


    private void sendToSpeaker(String message) throws
                                               Exception {
        speaker.receiveMessage(message.getBytes());
    }

    @SneakyThrows
    public void acceptUser(CharSequence text) {
        swapToCallingContext(text.toString());
        String key = new Random128bit().getResult();
        aesBuilder.append(key);
        rsaCipher.init(Cipher.ENCRYPT_MODE, new BasicConverter().publicKeyFromString(talker.getPublicKey()));
        String message = "ACCEPT" + new String(rsaCipher.doFinal(key.getBytes()));
        send(message.getBytes());
    }

    public void updateTalker(String nick) {
        for (VoIPUser user : users) {
            if (user.getNick().equals(nick)) {
                talker = user;
                return;
            }
        }
    }
}
