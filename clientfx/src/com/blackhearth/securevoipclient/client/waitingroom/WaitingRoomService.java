package com.blackhearth.securevoipclient.client.waitingroom;

import com.blackhearth.securevoipclient.client.user.VoIPUser;

import java.util.List;


public interface WaitingRoomService {
    List<VoIPUser> getUsers();
}
