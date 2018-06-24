package com.litosh.ilya.ct_sdk.callbacks;

import com.litosh.ilya.ct_sdk.models.messages.Chat;

import java.util.LinkedList;

/**
 * OnChatsGettingCallback для получения всех чатов
 *
 * Created by ilya_ on 24.06.2018.
 */

public interface OnChatsGettingCallback {

    /**
     * Вызывается при получении всех чатов
     *
     * @param chatLinkedList связный список всех чатов
     */
    void onChats(LinkedList<Chat> chatLinkedList);

}
