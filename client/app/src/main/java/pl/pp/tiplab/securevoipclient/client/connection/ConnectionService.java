package pl.pp.tiplab.securevoipclient.client.connection;

import pl.pp.tiplab.securevoipclient.client.connection.dto.VoIPConnectionResponse;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static pl.pp.tiplab.securevoipclient.client.connection.ConnectionConstants.ACCEPT_CONNECTION_ENDPOINT;
import static pl.pp.tiplab.securevoipclient.client.connection.ConnectionConstants.DISCONNECT_ENDPOINT;
import static pl.pp.tiplab.securevoipclient.client.connection.ConnectionConstants.REFUSE_CONNECTION_ENDPOINT;
import static pl.pp.tiplab.securevoipclient.client.connection.ConnectionConstants.TRY_CONNECTION_ENDPOINT;

public interface ConnectionService {
    @GET(TRY_CONNECTION_ENDPOINT)
    Call<VoIPConnectionResponse> tryConnectWith(@Header("initiatorsToken") String token,
                                                @Query("responderNick") String nick);

    @GET(ACCEPT_CONNECTION_ENDPOINT)
    Call<VoIPConnectionResponse> acceptConnection(@Header("respondersToken") String token,
                                                  @Query("sessionIdToken") String sessionToken);

    @GET(REFUSE_CONNECTION_ENDPOINT)
    Call<VoIPConnectionResponse> refuseConnection(@Query("sessionIdToken") String sessionToken,
                                                  @Header("respondersToken") String token);

    @GET(DISCONNECT_ENDPOINT)
    Call<VoIPConnectionResponse> disconnect(@Header("userToken") String token,
                                            @Query("sessionIdToken") String sessionToken);

}
