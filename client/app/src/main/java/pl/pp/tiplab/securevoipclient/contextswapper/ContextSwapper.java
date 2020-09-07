package pl.pp.tiplab.securevoipclient.contextswapper;

import android.os.AsyncTask;

import java.util.concurrent.locks.ReentrantLock;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.pp.tiplab.securevoipclient.ApplicationContext;
import pl.pp.tiplab.securevoipclient.R;
import pl.pp.tiplab.securevoipclient.client.UserController;
import pl.pp.tiplab.securevoipclient.client.register.ClientRegister;

@Setter
@RequiredArgsConstructor
public class ContextSwapper {

    private final ApplicationContext context;
    private final ClientRegister register;

    private final UserController userController;

    private ReentrantLock applicationLock;


    public void startApplication() {
        if (register.isUserRegistered()) {
            swapToWaitingRoom();
        } else {
            context.getContext()
                   .setContentView(R.layout.register_activity);
        }
    }


    public void swapToWaitingRoom() {
        AsyncTask.execute(() -> {
            context.setUsers(userController.getUsersFromServer());
            context.getContext()
                   .runOnUiThread(() -> context.getContext()
                                               .setContentView(R.layout.waiting_room_activity));
        });
    }

    public void swapToCallingRoom() {
        AsyncTask.execute(() -> {
            context.getContext()
                   .runOnUiThread(() -> context.getContext()
                                               .setContentView(R.layout.calling_activity));
        });
    }
}
