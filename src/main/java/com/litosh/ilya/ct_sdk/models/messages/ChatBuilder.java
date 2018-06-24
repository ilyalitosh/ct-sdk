package com.litosh.ilya.ct_sdk.models.messages;

/**
 * ChatBuilder для сборки объекта Chat
 *
 * Created by ilya_ on 24.06.2018.
 */

public class ChatBuilder {

    private Chat mChat;

    public ChatBuilder() {
        mChat = new Chat();
    }

    public ChatBuilder chatId(String chatId) {
        mChat.setChatId(chatId);
        return this;
    }

    public ChatBuilder urlChatImage(String urlChatImage) {
        mChat.setUrlChatImage(urlChatImage);
        return this;
    }

    public ChatBuilder chatName(String chatName) {
        mChat.setChatName(chatName);
        return this;
    }

    public ChatBuilder chatTime(String chatTime) {
        mChat.setChatTime(chatTime);
        return this;
    }

    public ChatBuilder chatLastMessage(String chatLastMessage) {
        mChat.setChatLastMessage(chatLastMessage);
        return this;
    }

    public ChatBuilder isContainsNewMessage(boolean isContainsNewMessage) {
        mChat.setContainsNewMessage(isContainsNewMessage);
        return this;
    }

    public Chat build() {
        return mChat;
    }

}
