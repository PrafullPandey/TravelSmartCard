package com.example.travelsmartcard.Network;

import com.example.travelsmartcard.Util.Constants;
import com.google.android.gms.common.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by p2 on 12/4/19.
 */

public class RestAPI {

    public static Retrofit adapter = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Api appService;

    public static Api getAppService() {
        appService = adapter.create(Api.class);
        return appService;
    }


}