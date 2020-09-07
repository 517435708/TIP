package com.blackhearth.securevoipclient.client;


import com.blackhearth.securevoipclient.client.user.VoIPUser;

import java.util.List;

public interface UserController {
    List<VoIPUser> getUsersFromServer();
}
