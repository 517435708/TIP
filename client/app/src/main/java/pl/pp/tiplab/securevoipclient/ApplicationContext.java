package pl.pp.tiplab.securevoipclient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
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
    private DatagramSocket socket;

    @SneakyThrows
    public ApplicationContext(AppCompatActivity context) {
        socket = new DatagramSocket(APPLICATION_PORT, InetAddress.getLocalHost());
        users = new ArrayList<>();
        data = new BasicClientData();
        this.context = context;
    }

}
