package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.messages.Message;

/**
 * OnMessageSendingCallback
 *
 * Created by ilya_ on 28.06.2018.
 */

public interface OnMessageSendingCallback {

    /**
     * Вызывается в результате успешной
     * отправки сообщения
     *
     * @param message сообщение
     */
    void onSuccess(Message message);

    /**
     * Вызывается, если произошла ошибка
     * при отправке сообщения
     *
     * @param error ошибка
     */
    void onError(String error);

}
