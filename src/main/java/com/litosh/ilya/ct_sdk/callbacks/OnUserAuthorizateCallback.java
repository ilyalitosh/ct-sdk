package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.Cookie;

/**
 * OnUserAuthorizateCallback
 *
 * Created by ilya_ on 17.06.2018.
 */

public interface OnUserAuthorizateCallback {

    /**
     * Вызывается при успешной авторизации
     * @param cookie куки с нужными данными для пользования сервисом
     * @param userId userId авторизовавшегося пользователя
     */
    void onSuccess(Cookie cookie, String userId);

    /**
     * Вызывается при ошибке авторизации
     * @param t throwable
     * @param errorMessage текст ошибки
     */
    void onError(Throwable t, String errorMessage);

}
