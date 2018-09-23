package com.litosh.ilya.ct_sdk.api;

import com.litosh.ilya.ct_sdk.callbacks.OnUserAuthorizateCallback;
import com.litosh.ilya.ct_sdk.models.Cookie;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Authorization
 * Класс для авторизации пользователя
 *
 * @author Ilya Litosh
 */
public class Authorization {

    /**
     * Выполняет авторизацию
     *
     * @param email email пользователя для авторизации
     * @param pass пароль пользователя для аторизации
     * @param onUserAuthorizateCallback колбэк авторизации
     */
    public void authorizate(final String email,
                                   String pass,
                                   final OnUserAuthorizateCallback onUserAuthorizateCallback) {
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
                                            onUserAuthorizateCallback.onSuccess(cookie, userId);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            onUserAuthorizateCallback.onError(e, e.toString());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            onUserAuthorizateCallback.onError(
                                    new Throwable(),
                                    "Server-error, code: " + stringResponse.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onUserAuthorizateCallback.onError(e, e.toString());
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
