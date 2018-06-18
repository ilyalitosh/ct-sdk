package com.litosh.ilya.ct_sdk.api.profile;

import com.litosh.ilya.ct_sdk.models.Cookie;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ilya_ on 16.06.2018.
 */

public interface ProfileApi {

    @FormUrlEncoded
    @POST("obr/login.php")
    Observable<Response<String>> authorizate(
            @Field("mail") String mail,
            @Field("pas") String pass,
            @Field("remember") String remember,
            @Field("insys") String insys);

    @FormUrlEncoded
    @POST("/obr/wallpost.php")
    Observable<ResponseBody> wallPost(
            @Header("Cookie") Cookie cookie,
            @Field("text") String text,
            @Field("id") String id,
            @Field("type") String type,
            @Field("stat") String stat,
            @Field("commwrt") String commwrt);

    @GET("/users/{user_id}")
    Observable<ResponseBody> getUser(
            @Header("Cookie") Cookie cookie,
            @Path("user_id") String userId);

    @GET("/users/{user_id}")
    Observable<ResponseBody> activatePhpSession(
            @Header("Cookie") Cookie cookie,
            @Path("user_id") String userId);

}
