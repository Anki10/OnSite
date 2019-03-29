package com.qci.onsite.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raj on 3/16/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;



    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.MINUTES)
                    .readTimeout(30,TimeUnit.MINUTES).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
