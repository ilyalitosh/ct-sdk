package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.messages.Chat;

/**
 * OnNewMessagesInChatsListListener слушатель
 * новых сообщений из списка чатов
 *
 * Created by ilya_ on 27.06.2018.
 */

public interface OnNewMessagesInChatsListListener {

    /**
     * Вызывается при новом сообщений
     *
     * @param chat Chat модель с обновленной информацией о сообщении
     */
    void onNewMessage(Chat chat);

}
