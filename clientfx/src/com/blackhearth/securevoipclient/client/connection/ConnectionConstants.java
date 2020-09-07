package com.blackhearth.securevoipclient.client.connection;

final class ConnectionConstants {

    static final String ACCEPT_CONNECTION_ENDPOINT = "/api/session/connection/accept";
    static final String REFUSE_CONNECTION_ENDPOINT = "/api/session/connection/refuse";
    static final String TRY_CONNECTION_ENDPOINT = "/api/session/tryConnect";
    static final String DISCONNECT_ENDPOINT = "/api/session/disconnect";

    private ConnectionConstants() {

    }
}
