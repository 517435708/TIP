package pl.pp.tiplab.securevoipclient.configuration;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import javax.crypto.Cipher;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.ApplicationContext;
import pl.pp.tiplab.securevoipclient.R;
import pl.pp.tiplab.securevoipclient.client.connection.ClientConnector;
import pl.pp.tiplab.securevoipclient.client.connection.dto.VoIPConnectionRequest;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import pl.pp.tiplab.securevoipclient.client.user.VoIPUser;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;
import pl.pp.tiplab.securevoipclient.rsa.Random128bit;

@Setter
@RequiredArgsConstructor
public class ButtonOnClickRegister {

    private final ApplicationContext context;
    private final ClientRegister clientRegister;
    private final ClientConnector clientConnector;
    private VoIPUser data;

    public void init() {
        context.getContext()
               .findViewById(R.id.end_call_button)
               .setOnClickListener(this::registerButtonCall);
    }

    @SneakyThrows
    private synchronized void registerButtonCall(View view) {
        RSAGenerator rsaGenerator = new RSAGenerator();
        context.getData()
               .setPrivateKey(rsaGenerator.getPrivateKey());
        context.getData()
               .setPublicKey(rsaGenerator.getPublicKey());

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNick(((EditText) context.getContext()
                                                   .findViewById(R.id.nickInput)).getText()
                                                                                 .toString());
        registerRequest.setPublicKey(context.getData()
                                            .getPublicKey()
                                            .toString());
        AsyncTask.execute(() -> {
            RegisterResponse response = clientRegister.registerUser(registerRequest);
            context.getData()
                   .setNickName(response.getNick());
            context.getData()
                   .setUserToken(response.getUserToken());
            context.setUsers(response.getUsers());
            context.send(response.getUserToken().getBytes());
            context.update();
        });
    }

    public synchronized void refuseCall(View view) {

    }

    public synchronized void callButton(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();

        if (color == Color.CYAN) {
            context.acceptUser(((Button) view).getText());
        } else {
            Map<String, String> callingUsersBySessionToken = context.getCallingUsersBySessionToken();

            VoIPConnectionRequest request = new VoIPConnectionRequest();
            request.setUserToken(context.getData().getUserToken());

            if (callingUsersBySessionToken.containsKey(data.getNick())) {
                request.setSessionId(callingUsersBySessionToken.get(data.getNick()));
                clientConnector.acceptConnection(request);
            } else {
                request.setResponderNick(data.getNick());
                context.updateTalker(data.getNick());
                clientConnector.tryConnectWith(request);
            }
        }
    }

    @SneakyThrows
    public void endCall(View view) {
        context.getMessagesToSend().put("END".getBytes());
        context.update();
    }
}
