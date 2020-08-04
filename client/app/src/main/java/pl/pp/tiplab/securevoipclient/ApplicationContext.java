package pl.pp.tiplab.securevoipclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.configuration.ButtonOnClickRegister;
import pl.pp.tiplab.securevoipclient.contextswapper.ContextSwapper;
import pl.pp.tiplab.securevoipclient.client.user.VoIPUser;

@Getter
@Setter
public class ApplicationContext {

    private List<VoIPUser> users;
    private Map<String, String> callingUsersBySessionToken;
    private BasicClientData data;
    private AppCompatActivity context;
    private ButtonOnClickRegister register;

    private BlockingQueue<byte[]> receivedMessages;
    private BlockingQueue<byte[]> messagesToSend;

    private ContextSwapper swapper;

    @SneakyThrows
    public ApplicationContext(AppCompatActivity context) {
        receivedMessages = new LinkedBlockingQueue<>();
        messagesToSend = new LinkedBlockingQueue<>();

        users = new ArrayList<>();
        data = new BasicClientData();
        this.context = context;
    }

    public void send(byte[] message) {
        messagesToSend.add(message);
    }

    public void put(DatagramPacket pack) {
        receivedMessages.add(pack.getData());
    }

    public void update() {
        swapper.swapToWaitingRoom();
        context.runOnUiThread(this::updateUserInWaitingRoom);
    }

    @SuppressLint("ResourceType")
    private void updateUserInWaitingRoom() {
        LinearLayout layout = context.findViewById(R.id.waiting_room_layout);
        for (VoIPUser user : users) {
            Button button = new Button(context);
            button.setText(user.getNick());
            button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            register.setData(user);
            button.setOnClickListener(register::callButton);
            layout.addView(button);
        }
    }

}
