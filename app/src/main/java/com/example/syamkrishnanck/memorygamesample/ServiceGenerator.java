package com.example.syamkrishnanck.memorygamesample;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by syamkrishnanck on 3/18/16.
 */
public class ServiceGenerator {
    private static FlickerClient service;
    public static final String API_BASE_URL = "https://api.flickr.com";


    static Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(new ToStringConverterFactory())
            .build();

    public static FlickerClient createService() {
        return retrofit.create(FlickerClient.class);
    }
}
