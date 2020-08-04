package pl.pp.tiplab.securevoipclient.client.connection.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class VoIPConnectionRequest {
    private String responderNick;
    private String userToken;
    private String sessionId;
}
