package com.litosh.ilya.ct_sdk.api.messages;

import com.litosh.ilya.ct_sdk.models.BaseCookie;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Интерфейс messages api
 *
 * Created by ilya_ on 17.06.2018.
 */

public interface MessagesApi {

    @FormUrlEncoded
    @POST("/obr/mailget.php")
    Observable<ResponseBody> listenMessages(
            @Header("Cookie") BaseCookie cookie,
            @Field("iduser") String userId);

    @FormUrlEncoded
    @POST("/obr/mailsend.php")
    Observable<ResponseBody> sendMessage(
            @Header("Cookie") BaseCookie cookie,
            @Field("iduser") String userId,
            @Field("text") String message);

    @GET("/mail")
    Observable<ResponseBody> getChats(
            @Header("Cookie") BaseCookie cookie);

    @GET("/mail/{chat_id}")
    Observable<ResponseBody> getMessagesInChat(
            @Header("Cookie") BaseCookie cookie,
            @Path("chat_id") String chatId);

}
