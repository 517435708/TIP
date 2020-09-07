package com.blackhearth.securevoipclient.client.connection;

import com.blackhearth.securevoipclient.client.connection.dto.VoIPConnectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.blackhearth.securevoipclient.ApplicationConstants.APPLICATION_ENDPOINT;
import static com.blackhearth.securevoipclient.client.connection.ConnectionConstants.*;


@Service
@RequiredArgsConstructor
public class BasicClientConnector implements ConnectionService {


    private final RestTemplate restTemplate;


    @Override
    public VoIPConnectionResponse tryConnectWith(String initiatorsToken, String responderNick) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("initiatorsToken", initiatorsToken);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET,
                                                        URI.create(APPLICATION_ENDPOINT + TRY_CONNECTION_ENDPOINT + "?responderNick=" + responderNick));
        return restTemplate.exchange(requestEntity, VoIPConnectionResponse.class).getBody();
    }

    @Override
    public VoIPConnectionResponse acceptConnection(String responderToken, String sessionToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("respondersToken", responderToken);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET,
                                                        URI.create(APPLICATION_ENDPOINT + ACCEPT_CONNECTION_ENDPOINT + "?sessionIdToken=" + sessionToken));
        return restTemplate.exchange(requestEntity, VoIPConnectionResponse.class).getBody();
    }

    @Override
    public VoIPConnectionResponse refuseConnection(String responderToken, String sessionToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("respondersToken", responderToken);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET,
                                                        URI.create(APPLICATION_ENDPOINT + REFUSE_CONNECTION_ENDPOINT + "?sessionIdToken=" + sessionToken));
        return restTemplate.exchange(requestEntity, VoIPConnectionResponse.class).getBody();
    }

    @Override
    public VoIPConnectionResponse disconnect(String userToken, String sessionToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userToken", userToken);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET,
                                                        URI.create(APPLICATION_ENDPOINT + DISCONNECT_ENDPOINT + "?sessionIdToken=" + sessionToken));
        return restTemplate.exchange(requestEntity, VoIPConnectionResponse.class).getBody();
    }
}
