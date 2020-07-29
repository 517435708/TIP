package pl.pp.tiplab.securevoipclient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.user.VoIPUser;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_PORT;

@Getter
@Setter
public class ApplicationContext {

    private List<VoIPUser> users;
    private BasicClientData data;
    private AppCompatActivity context;

    private Deque<String> receivedMessages;
    private Deque<String> messagesToSend;

    private boolean running = true;

    @SneakyThrows
    public ApplicationContext(AppCompatActivity context) {
        receivedMessages = new LinkedList<>();
        messagesToSend = new LinkedList<>();
        
        users = new ArrayList<>();
        data = new BasicClientData();
        this.context = context;
    }

    public boolean isRunning() {
        return running;
    }

    public void send(String message) {
        messagesToSend.add(message);
    }

    public void put(DatagramPacket pack) {
        receivedMessages.add(new String(pack.getData()));
    }

    public String pop() {
        return receivedMessages.getFirst();
    }

    public boolean isEmpty() {
        return messagesToSend.isEmpty();
    }
}
