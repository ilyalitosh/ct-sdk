package com.litosh.ilya.ct_sdk.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.litosh.ilya.ct_sdk.api.messages.MessagesApi;
import com.litosh.ilya.ct_sdk.api.profile.ProfileApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ilya_ on 17.06.2018.
 */

final class CtApi {

    private static ProfileApi profileApi;
    private static MessagesApi messagesApi;

    private CtApi() {

    }

    /**
     * Инициализируем api service
     */
    public static void init() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cubingtime.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        profileApi = retrofit.create(ProfileApi.class);
        messagesApi = retrofit.create(MessagesApi.class);
    }

    /**
     * Получить profile api
     * @return profileApi
     */
    public static ProfileApi getProfileApi() {
        return profileApi;
    }

    /**
     * Получить message api
     * @return messagesApi
     */
    public static MessagesApi getMessagesApi() {
        return messagesApi;
    }

}
