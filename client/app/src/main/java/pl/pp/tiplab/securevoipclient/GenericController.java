package pl.pp.tiplab.securevoipclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static pl.pp.tiplab.securevoipclient.ApplicationConstants.APPLICATION_ENDPOINT;

public class GenericController {

    protected Retrofit retrofit;

    public GenericController() {
        Gson gson = new GsonBuilder().setLenient()
                                     .create();
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                                         .baseUrl(APPLICATION_ENDPOINT)
                                         .build();
    }


}
