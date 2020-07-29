package pl.pp.tiplab.securevoipclient.configuration;

import android.view.View;
import android.widget.EditText;

import lombok.RequiredArgsConstructor;
import pl.pp.tiplab.securevoipclient.ApplicationContext;
import pl.pp.tiplab.securevoipclient.R;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;

@RequiredArgsConstructor
public class ButtonOnClickRegister {

    private final ApplicationContext context;
    private final ClientRegister clientRegister;

    public void init() {
        context.getContext().findViewById(R.id.heheheheh_xD).setOnClickListener(this::registerButtonCall);
    }

    private void registerButtonCall(View view) {
        RSAGenerator rsaGenerator = new RSAGenerator();
        context.getData().setPrivateKey(rsaGenerator.getPrivateKey());
        context.getData().setPublicKey(rsaGenerator.getPublicKey());

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNick(((EditText)context.getContext().findViewById(R.id.nickInput)).getText().toString());
        registerRequest.setPublicKey(context.getData().getPublicKey().toString());
        RegisterResponse response = clientRegister.registerUser(registerRequest);
        context.getData().setNickName(response.getNick());
        context.getData().setUserToken(response.getUserToken());
        context.setUsers(response.getUsers());
    }

}
