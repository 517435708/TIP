package pl.pp.tiplab.securevoipclient.client.network;

import java.io.IOException;

public class NetworkException extends RuntimeException {
    public NetworkException(Exception e) {
        super(e);
    }
}
