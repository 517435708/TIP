package pl.pp.tiplab.securevoipclient.client.connection.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class VoIPConnectionResponse implements Serializable {
    private String message;
}
