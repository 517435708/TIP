package com.bluevortexflare.securevoip.session;

import com.bluevortexflare.securevoip.users.connection.UserConnectionService;
import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;
import com.bluevortexflare.securevoip.session.UserSessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private UserConnectionService connectionService;

    SessionController(UserConnectionService userConnectionService) {
        this.connectionService = userConnectionService;
    }

    @GetMapping("/tryConnect")
    public ConnectionResponse tryConnect(@RequestHeader String initiatorsToken,
                                         @RequestParam String responderNick) {
        return connectionService.tryConnectWith(initiatorsToken, responderNick);
    }

    @GetMapping("/connection/accept")
    public ConnectionResponse acceptConnection(@RequestHeader String respondersToken,
                                               @RequestParam String sessionIdToken) {
        return connectionService.connect(respondersToken, sessionIdToken);
    }

    @GetMapping("/connection/refuse")
    public ConnectionResponse refuseConnection(@RequestParam String sessionIdToken,
                                               @RequestHeader String respondersToken) {
        return connectionService.refuse(respondersToken, sessionIdToken);
    }

    @GetMapping("/disconnect")
    public ConnectionResponse disconnect(@RequestHeader String userToken,
                                         @RequestParam String sessionIdToken) {
        return connectionService.disconnect(userToken, sessionIdToken);
    }
}
