package com.litosh.ilya.ct_sdk.api.messages;

import com.litosh.ilya.ct_sdk.api.Cookie;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Интерфейс messages api
 *
 * Created by ilya_ on 17.06.2018.
 */

public interface MessagesApi {

    @FormUrlEncoded
    @POST("/obr/mailget.php")
    Observable<ResponseBody> getMessages(
            @Header("Cookie") Cookie cookie,
            @Field("iduser") String userId);

}
