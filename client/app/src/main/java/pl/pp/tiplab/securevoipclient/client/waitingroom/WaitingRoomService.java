package pl.pp.tiplab.securevoipclient.client.waitingroom;

import java.util.List;

import pl.pp.tiplab.securevoipclient.user.VoIPUser;
import retrofit2.http.POST;

import static pl.pp.tiplab.securevoipclient.client.waitingroom.WaitingRoomConstants.WAITING_ROOM_ENDPOINT;

public interface WaitingRoomService {
    @POST(WAITING_ROOM_ENDPOINT)
    List<VoIPUser> getUsers();
}
