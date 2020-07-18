package pl.pp.tiplab.securevoipclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;


import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

import pl.pp.tiplab.securevoipclient.cryptographic.Calling;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;
import pl.pp.tiplab.securevoipclient.Utils.IpUtil;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.client.register.BasicClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;


public class MainActivity extends AppCompatActivity {




    @RequiresApi(api = Build.VERSION_CODES.N)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BasicClientData clientData = new BasicClientData();
        RSAGenerator rsaGenerator = new RSAGenerator();
        ClientRegister register = new BasicClientRegister(clientData, new RestTemplate(), rsaGenerator);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
