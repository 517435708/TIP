package com.bluevortexflare.securevoip.users.connection;

import com.bluevortexflare.securevoip.communication.CommunicationForwarderService;
import com.bluevortexflare.securevoip.users.connection.dto.ConnectionResponse;
import com.bluevortexflare.securevoip.session.UserSessionService;
import org.springframework.stereotype.Service;

@Service
public class BasicUserConnectionService implements UserConnectionService {

    private static final String OK = "OK";
    private static final String BUSY = "BUSY";


    private CommunicationForwarderService forwarderService;
    private UserSessionService sessionService;

    BasicUserConnectionService(CommunicationForwarderService forwarderService,
                               UserSessionService sessionService) {
        this.forwarderService = forwarderService;
        this.sessionService = sessionService;
    }

    @Override
    public ConnectionResponse connect(String respondersSessionToken, String sessionIdToken) {
        sessionService.addUserToSession(respondersSessionToken, sessionIdToken);
        return new ConnectionResponse(OK);
    }

    @Override
    public ConnectionResponse refuse(String respondersSessionToken, String sessionIdToken) {
        String initiatorsToken = sessionService.killSession(respondersSessionToken, sessionIdToken);
        forwarderService.sendRefuseMessage(initiatorsToken);

        return new ConnectionResponse(OK);
    }

    @Override
    public ConnectionResponse tryConnectWith(String initiatorsToken, String responderNick) {
        if (userIsNotConnected(responderNick)) {
            String sessionIdToken = sessionService.createNewSession(initiatorsToken, responderNick);
            forwarderService.makeCall(initiatorsToken, responderNick, sessionIdToken);
            return new ConnectionResponse(OK);
        }
        return new ConnectionResponse(BUSY);
    }

    @Override
    public ConnectionResponse disconnect(String userToken, String sessionIdToken) {
        String secondUserToken = sessionService.killSession(userToken, sessionIdToken);
        forwarderService.sendRefuseMessage(secondUserToken);
        return new ConnectionResponse(OK);
    }


    private boolean userIsNotConnected(String responderNick) {
        return true;
    }
}
