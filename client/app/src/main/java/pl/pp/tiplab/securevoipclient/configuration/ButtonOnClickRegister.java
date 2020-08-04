package pl.pp.tiplab.securevoipclient.configuration;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

@Setter
@RequiredArgsConstructor
public class ButtonOnClickRegister {

    private final ApplicationContext context;
    private final ClientRegister clientRegister;
    private final ClientConnector clientConnector;
    private VoIPUser data;

    public void init() {
        context.getContext()
               .findViewById(R.id.heheheheh_xD)
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
            context.update();
        });
    }

    public synchronized void refuseCall(View view) {

    }

    public synchronized void callButton(View view) {
        Map<String, String> callingUsersBySessionToken = context.getCallingUsersBySessionToken();

        VoIPConnectionRequest request = new VoIPConnectionRequest();
        request.setUserToken(context.getData().getUserToken());

        if (callingUsersBySessionToken.containsKey(data.getNick())) {
            request.setSessionId(callingUsersBySessionToken.get(data.getNick()));
            clientConnector.acceptConnection(request);
        } else {
            request.setResponderNick(data.getNick());
            clientConnector.tryConnectWith(request);
        }
    }

}
