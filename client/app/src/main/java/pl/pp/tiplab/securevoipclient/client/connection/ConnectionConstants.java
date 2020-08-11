package pl.pp.tiplab.securevoipclient.client.connection;

final class ConnectionConstants {

    static final String ACCEPT_CONNECTION_ENDPOINT = "/api/session/connect/accept";
    static final String REFUSE_CONNECTION_ENDPOINT = "/api/session/connection/refuse";
    static final String TRY_CONNECTION_ENDPOINT = "/api/session/connect";
    static final String DISCONNECT_ENDPOINT = "/api/session/disconnect";

    private ConnectionConstants() {

    }
}
