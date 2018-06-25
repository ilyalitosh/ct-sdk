package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.messages.Message;

import java.util.LinkedList;

/**
 * OnGettingMessagesInChatCallback для получения
 * сообщений в чате
 *
 * Created by ilya_ on 25.06.2018.
 */

public interface OnGettingMessagesInChatCallback {

    /**
     * Вызывается при получении сообщений из чата
     *
     * @param messages сообщения из чата
     */
    void onMessages(LinkedList<Message> messages);

}
