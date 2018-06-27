package com.litosh.ilya.ct_sdk.callbacks;

/**
 * OnNewMessageListener слушатель новых сообщений
 *
 * Created by ilya_ on 18.06.2018.
 */

public interface OnNewMessageListener {

    /**
     * Вызывается при новом сообщений
     *
     * @param message сообщение
     */
    void onNewMessage(String message);

}
