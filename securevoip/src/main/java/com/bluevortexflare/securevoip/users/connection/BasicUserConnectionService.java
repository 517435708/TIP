package com.bluevortexflare.securevoip.users.connection;

import com.bluevortexflare.securevoip.users.connection.UserConnectionService;
import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;
import org.springframework.stereotype.Service;

@Service
public class BasicUserConnectionService implements UserConnectionService {
    @Override
    public ConnectionResponse tryConnectWith(String sessionToken) {
        return null;
    }

    @Override
    public ConnectionResponse connect(String sessionToken) {
        return null;
    }

    @Override
    public ConnectionResponse refuse(String sessionToken, String requestId) {
        return null;
    }
}
