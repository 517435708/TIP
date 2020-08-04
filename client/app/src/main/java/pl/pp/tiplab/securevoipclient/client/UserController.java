package pl.pp.tiplab.securevoipclient.client;

import java.util.List;

import pl.pp.tiplab.securevoipclient.client.user.VoIPUser;

public interface UserController {
    List<VoIPUser> getUsersFromServer();
}
