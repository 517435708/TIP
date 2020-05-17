package com.bluevortexflare.securevoip.session;

import com.bluevortexflare.securevoip.users.register.VoIPUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class VoIPSession {

    private String sessionId;

    private VoIPUser initiator;
    private VoIPUser responder;
}
