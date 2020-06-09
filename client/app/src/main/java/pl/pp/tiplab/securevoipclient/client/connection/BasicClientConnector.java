package pl.pp.tiplab.securevoipclient.client.connection;

import pl.pp.tiplab.securevoipclient.client.connection.ClientConnector;
import pl.pp.tiplab.securevoipclient.client.connection.dto.VoIPConnectionRequest;

public class BasicClientConnector implements ClientConnector {
    @Override
    public String tryConnectWith(VoIPConnectionRequest request) {
        return null;
    }

    @Override
    public String acceptConnection(VoIPConnectionRequest request) {
        return null;
    }

    @Override
    public String rejectConnection(VoIPConnectionRequest request) {
        return null;
    }

    @Override
    public String disconnect(VoIPConnectionRequest request) {
        return null;
    }
}
