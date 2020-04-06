package com.bluevortexflare.securevoip.users.connection;


import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;

public interface UserConnectionService {
    ConnectionResponse tryConnectWith(String sessionToken);
    ConnectionResponse connect(String sessionToken);
    ConnectionResponse refuse(String sessionToken, String requestId);
}
