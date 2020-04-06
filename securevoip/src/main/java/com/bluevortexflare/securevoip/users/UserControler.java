package com.bluevortexflare.securevoip.users;

import com.bluevortexflare.securevoip.users.connection.UserConnectionService;
import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;
import com.bluevortexflare.securevoip.users.register.UserRegisterService;
import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import com.bluevortexflare.securevoip.users.session.dto.SessionResponse;
import com.bluevortexflare.securevoip.users.session.UserSessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserControler {

    private final UserSessionService sessionService;
    private final UserConnectionService connectionService;
    private final UserRegisterService registerService;


    UserControler(UserRegisterService registerService,
                  UserConnectionService connectionService,
                  UserSessionService sessionService) {
        this.registerService = registerService;
        this.connectionService = connectionService;
        this.sessionService = sessionService;
    }


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return registerService.registerUser(request);
    }

    @GetMapping("/connect")
    public ConnectionResponse connect(@RequestHeader String sessionToken) {
        return connectionService.tryConnectWith(sessionToken);
    }

    @GetMapping("/session/refresh")
    public SessionResponse validateSession(@RequestParam String userId,
                                           @RequestHeader String password) {
        return sessionService.refreshToken(userId, password);
    }

    @GetMapping("/connect/accept")
    public ConnectionResponse acceptConnection(@RequestHeader String sessionToken) {
        return connectionService.connect(sessionToken);
    }

    @GetMapping("/connection/refuse")
    public ConnectionResponse connectionRespons(@RequestParam String requestId,
                                                @RequestHeader String sessionToken) {
        return connectionService.refuse(sessionToken, requestId);
    }

}
