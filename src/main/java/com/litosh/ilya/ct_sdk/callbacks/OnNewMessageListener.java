package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.messages.Message;

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
    void onNewMessage(Message message);

}
