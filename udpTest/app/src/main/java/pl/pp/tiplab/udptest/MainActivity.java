package pl.pp.tiplab.udptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.net.SocketException;

import pl.pp.tiplab.udptest.client.UdpClient;
import pl.pp.tiplab.udptest.server.UdpServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            new Thread(new UdpClient()).start();
            new Thread(new UdpServer()).start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
