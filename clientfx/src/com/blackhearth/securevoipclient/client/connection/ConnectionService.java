package com.blackhearth.securevoipclient.client.connection;

import com.blackhearth.securevoipclient.client.connection.dto.VoIPConnectionResponse;

public interface ConnectionService {
    VoIPConnectionResponse tryConnectWith(String initiatorsToken, String responderNick);
    VoIPConnectionResponse acceptConnection(String responderToken, String sessionToken);
    VoIPConnectionResponse refuseConnection(String responderToken, String sessionToken);
    VoIPConnectionResponse disconnect(String userToken, String sessionToken);

}
