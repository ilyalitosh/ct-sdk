package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.User;

/**
 *
 * Created by ilya_ on 21.06.2018.
 */

public interface OnGettingUserCallback {

    /**
     * Вызывается при получении юзера и
     * возвращает информацию юзера
     *
     * @param user юзер
     */
    void onUser(User user);

}
