package pl.pp.tiplab.securevoipclient;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.client.BasicUserController;
import pl.pp.tiplab.securevoipclient.client.UserController;
import pl.pp.tiplab.securevoipclient.client.connection.BasicClientConnector;
import pl.pp.tiplab.securevoipclient.client.connection.ClientConnector;
import pl.pp.tiplab.securevoipclient.client.network.ClientListen;
import pl.pp.tiplab.securevoipclient.client.network.ClientSender;
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

        ClientListen clientListener = new ClientListen(applicationContext.getReceivedMessages());
        ClientSender clientSender = new ClientSender(applicationContext.getMessagesToSend());

        new Thread(clientListener).start();
        new Thread(clientSender).start();
        new Thread(applicationContext).start();

        ClientConnector clientConnector = new BasicClientConnector();
        ClientRegister clientRegister = new BasicClientRegister(applicationContext);
        UserController userController = new BasicUserController();
        ButtonOnClickRegister buttonOnClickRegister = new ButtonOnClickRegister(applicationContext,
                                                                                clientRegister,
                                                                                clientConnector);
        ContextSwapper contextSwapper = new ContextSwapper(applicationContext,
                                                           clientRegister,
                                                           userController);
        applicationContext.setSwapper(contextSwapper);
        applicationContext.setRegister(buttonOnClickRegister);

        contextSwapper.startApplication();
        buttonOnClickRegister.init();
    }
}
