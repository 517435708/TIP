package pl.pp.tiplab.securevoipclient.contextswapper;

import android.os.AsyncTask;

import lombok.RequiredArgsConstructor;
import pl.pp.tiplab.securevoipclient.ApplicationContext;
import pl.pp.tiplab.securevoipclient.R;
import pl.pp.tiplab.securevoipclient.client.UserController;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;

@RequiredArgsConstructor
public class ContextSwapper {

    private final ApplicationContext context;
    private final ClientRegister register;

    private final UserController userController;


    public void startApplication() {
        if (register.isUserRegistered()) {
            swapToWaitingRoom();
        } else {
            context.getContext()
                   .setContentView(R.layout.register_activity);
        }
    }


    public void swapToWaitingRoom() {
        if (context.getUsers() == null) {
            AsyncTask.execute(() -> {
                context.setUsers(userController.getUsersFromServer());
                context.getContext()
                       .runOnUiThread(() -> context.getContext()
                                                   .setContentView(R.layout.waiting_room_activity));
            });
        } else {
            context.getContext()
                   .runOnUiThread(() -> context.getContext()
                                               .setContentView(R.layout.waiting_room_activity));
        }
    }
}
