package pl.pp.tiplab.securevoipclient;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.BasicUserController;
import pl.pp.tiplab.securevoipclient.client.UserController;
import pl.pp.tiplab.securevoipclient.client.register.BasicClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;
import pl.pp.tiplab.securevoipclient.configuration.ButtonOnClickRegister;
import pl.pp.tiplab.securevoipclient.contextswapper.ContextSwapper;


public class MainActivity extends AppCompatActivity {


    @Override
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationContext applicationContext = new ApplicationContext(this);
        ClientRegister clientRegister = new BasicClientRegister(this);
        UserController userController = new BasicUserController();
        ButtonOnClickRegister buttonOnClickRegister = new ButtonOnClickRegister(applicationContext, clientRegister);
        ContextSwapper contextSwapper = new ContextSwapper(applicationContext, clientRegister, userController);

        buttonOnClickRegister.init();
        contextSwapper.startApplication();
    }
}
