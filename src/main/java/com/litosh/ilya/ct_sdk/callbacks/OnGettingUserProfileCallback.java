package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.profile.User;
import com.litosh.ilya.ct_sdk.models.profile.Wall;

/**
 * OnGettingUserProfileCallback
 *
 * @author Ilya Litosh
 */
public interface OnGettingUserProfileCallback {

    /**
     * Вызывается при получении юзера и возвращает
     * информацию о юзере и данные стены
     *
     * @param user юзер
     */
    void onUser(User user, Wall wall);

}
