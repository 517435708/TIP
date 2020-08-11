package pl.pp.tiplab.securevoipclient.client.connection;

import java.util.Objects;

import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.GenericController;
import pl.pp.tiplab.securevoipclient.client.connection.dto.VoIPConnectionRequest;

public class BasicClientConnector extends GenericController implements ClientConnector {

    private ConnectionService service;

    public BasicClientConnector() {
        super();
        service = retrofit.create(ConnectionService.class);
    }

    @SneakyThrows
    @Override
    public String tryConnectWith(VoIPConnectionRequest request) {
        return Objects.requireNonNull(service.tryConnectWith(request.getUserToken(),
                                                             request.getResponderNick())
                                             .execute()
                                             .body())
                      .getMessage();
    }

    @SneakyThrows
    @Override
    public String acceptConnection(VoIPConnectionRequest request) {
        return Objects.requireNonNull(service.acceptConnection(request.getUserToken(),
                                                               request.getSessionId())
                                             .execute()
                                             .body())
                      .getMessage();
    }

    @SneakyThrows
    @Override
    public String rejectConnection(VoIPConnectionRequest request) {
        return Objects.requireNonNull(service.refuseConnection(request.getSessionId(),
                                                               request.getUserToken())
                                             .execute()
                                             .body())
                      .getMessage();
    }

    @Override
    @SneakyThrows
    public String disconnect(VoIPConnectionRequest request) {
        return Objects.requireNonNull(service.disconnect(request.getUserToken(),
                                                         request.getSessionId())
                                             .execute()
                                             .body())
                      .getMessage();
    }
}
