package pl.pp.tiplab.securevoipclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;


import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;
import pl.pp.tiplab.securevoipclient.Utils.IpUtil;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.client.register.BasicClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;


public class MainActivity extends AppCompatActivity {

    private ClientRegister register;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BasicClientData clientData = new BasicClientData();
        RSAGenerator rsaGenerator = new RSAGenerator();
        register = new BasicClientRegister(clientData, new RestTemplate(), rsaGenerator);

        if (!register.isUserRegistered()) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setNick(//?);
            registerRequest.setPublicKey(rsaGenerator.getPublicKey().toString());
            registerRequest.setHost(IpUtil.getIPAddress(true));
            register.registerUser(registerRequest);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
