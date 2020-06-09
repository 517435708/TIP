package com.bluevortexflare.securevoip.users.connection;


import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;

public interface UserConnectionService {
    ConnectionResponse connect(String respondersToken, String sessionIdToken);
    ConnectionResponse refuse(String respondersToken, String sessionIdToken);

    ConnectionResponse tryConnectWith(String initiatorsToken, String responderNick);
    ConnectionResponse disconnect(String userToken, String sessionIdToken);
}
