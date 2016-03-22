package com.example.syamkrishnanck.memorygamesample;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.GET;


/**
 * Created by syamkrishnanck on 3/18/16.
 */
public interface FlickerClient {
    @GET("/services/feeds/photos_public.gne?format=json")
    Call<String> feeds();
}
