package com.bluevortexflare.securevoip.users.session;

import com.bluevortexflare.securevoip.users.session.dto.SessionResponse;

public interface UserSessionService {
    SessionResponse refreshToken(String userId, String password);

}
