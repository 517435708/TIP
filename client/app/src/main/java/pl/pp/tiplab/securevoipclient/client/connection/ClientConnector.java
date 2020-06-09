package pl.pp.tiplab.securevoipclient.client.connection;

import pl.pp.tiplab.securevoipclient.client.connection.dto.VoIPConnectionRequest;

public interface ClientConnector {
    String tryConnectWith(VoIPConnectionRequest request);
    String acceptConnection(VoIPConnectionRequest request);
    String rejectConnection(VoIPConnectionRequest request);
    String disconnect(VoIPConnectionRequest request);
}
