package com.bluevortexflare.securevoip.users.session;

import com.bluevortexflare.securevoip.users.session.dto.SessionResponse;
import org.springframework.stereotype.Service;

@Service
public class BasicUserSessionService implements UserSessionService {
    @Override
    public SessionResponse refreshToken(String userId, String password) {
        return null;
    }
}
