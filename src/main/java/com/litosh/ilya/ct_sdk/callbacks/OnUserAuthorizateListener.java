package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.Cookie;

/**
 * Created by ilya_ on 17.06.2018.
 */

public interface OnUserAuthorizateListener {

    /**
     * Вызывается при успешной авторизации
     * @param cookie куки с нужными данными для пользования сервисом
     * @param userId userId авторизовавшегося пользователя
     */
    void onSuccess(Cookie cookie, String userId);

    /**
     * Вызывается при ошибке авторизации
     * @param t throwable
     */
    void onError(Throwable t);

}
