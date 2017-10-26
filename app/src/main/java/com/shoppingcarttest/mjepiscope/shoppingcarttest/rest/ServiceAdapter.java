package com.shoppingcarttest.mjepiscope.shoppingcarttest.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KaeL on 25/10/17.
 */

public class ServiceAdapter {
    private static final String BASE_URL = "http://10.0.2.2:5000";
    private static Retrofit adapter;

    public static Retrofit getAdapter() {
        if (adapter != null)
            return adapter;

        initAdapter();
        return adapter;
    }

    private static void initAdapter() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        adapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
