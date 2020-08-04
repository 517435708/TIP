package pl.pp.tiplab.securevoipclient.client;

import java.util.List;

import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.GenericController;
import pl.pp.tiplab.securevoipclient.client.waitingroom.WaitingRoomService;
import pl.pp.tiplab.securevoipclient.client.user.VoIPUser;

public class BasicUserController extends GenericController implements UserController {

    private final WaitingRoomService waitingRoomService;

    public BasicUserController() {
        super();
        waitingRoomService = retrofit.create(WaitingRoomService.class);
    }

    @Override
    @SneakyThrows
    public List<VoIPUser> getUsersFromServer() {
        return waitingRoomService.getUsers().execute().body();
    }
}
