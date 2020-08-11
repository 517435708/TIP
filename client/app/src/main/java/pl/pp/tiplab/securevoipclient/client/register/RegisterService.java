package pl.pp.tiplab.securevoipclient.client.register;


import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.REGISTER_ENDPOINT;

public interface RegisterService {
    @POST(REGISTER_ENDPOINT)
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);
}
