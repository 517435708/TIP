package com.blackhearth.securevoipclient.client.network;

public class NetworkException extends RuntimeException {
    public NetworkException(Exception e) {
        super(e);
    }
}
