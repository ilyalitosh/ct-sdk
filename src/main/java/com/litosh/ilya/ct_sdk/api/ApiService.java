package com.litosh.ilya.ct_sdk.api;

import com.litosh.ilya.ct_sdk.callbacks.OnNewMessageListener;
import com.litosh.ilya.ct_sdk.callbacks.OnUserAuthorizateListener;
import com.litosh.ilya.ct_sdk.models.BaseCookie;
import com.litosh.ilya.ct_sdk.models.Cookie;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * ApiService
 *
 * Created by ilya_ on 17.06.2018.
 */

public class ApiService {

    public static void init(){
        CtApi.init();
    }

    public static void authorizate(final String email,
                                   String pass,
                                   final OnUserAuthorizateListener onUserAuthorizateListener) {
        CtApi.getProfileApi()
                .authorizate(email, pass, "on", "on")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        if (stringResponse.body().length() > 4) {
                            final Cookie cookie = new Cookie();
                            cookie.setCbtl(email);
                            cookie.setCbtp(getPassHash(stringResponse));
                            cookie.setLang("ru");
                            cookie.setNight("0");
                            cookie.setNoprev("1");
                            cookie.setPhpSessId(getPhpSessId(stringResponse));
                            final String userId = getUserId(stringResponse);
                            CtApi.getProfileApi()
                                    .activatePhpSession(cookie, userId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ResponseBody>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ResponseBody responseBody) {
                                            onUserAuthorizateListener.onSuccess(cookie, userId);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            onUserAuthorizateListener.onError(e, e.toString());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            onUserAuthorizateListener.onError(
                                    new Throwable(),
                                    "Server-error, code: " + stringResponse.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onUserAuthorizateListener.onError(e, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void listenNewMessages(final BaseCookie cookie, final OnNewMessageListener onNewMessageListener) {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        CtApi.getMessagesApi()
                                .getMessages(cookie, "")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ResponseBody responseBody) {
                                        try {
                                            onNewMessageListener.onNewMessage(responseBody.string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static String getPhpSessId(Response<String> response) {
        List<String> headers = response.headers().values("Set-Cookie");
        return headers.get(0).split("PHPSESSID=")[1].split(";")[0];
    }

    private static String getPassHash(Response<String> response) {
        return response.body().split("\\|")[0];
    }
    private static String getUserId(Response<String> response) {
        return "id" + response.body().split("\\|")[1];
    }

}
