package com.litosh.ilya.ct_sdk.api.profile;

import com.litosh.ilya.ct_sdk.models.BaseCookie;

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
 *
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
            @Header("Cookie") BaseCookie cookie,
            @Field("text") String text,
            @Field("id") String id,
            @Field("type") String type,
            @Field("stat") String stat,
            @Field("commwrt") String commwrt);

    @GET("/users/{user_id}")
    Observable<ResponseBody> getUser(
            @Header("Cookie") BaseCookie cookie,
            @Path("user_id") String userId);

    @GET("/users/{user_id}")
    Observable<ResponseBody> activatePhpSession(
            @Header("Cookie") BaseCookie cookie,
            @Path("user_id") String userId);

    /**
     * Выполняет лайк поста
     *
     * @param cookie куки
     * @param post id поста
     * @param type тип лайка(лайк поста или лайк комментария в посте)
     * @param act актуальность лайка(ставить лайк или убрать лайк)
     */
    @FormUrlEncoded
    @POST("/obr/walllike.php")
    Observable<Response<Void>> likePost(
            @Header("Cookie") BaseCookie cookie,
            @Field("post") String post,
            @Field("type") String type,
            @Field("act") String act);

}
