package com.litosh.ilya.ct_sdk.models.messages;

/**
 * MessageBuilder для сборки сущности Message
 *
 * Created by ilya_ on 25.06.2018.
 */

public class MessageBuilder {

    private Message mMessage;

    public MessageBuilder() {
        mMessage = new Message();
    }

    public MessageBuilder userName(String userName) {
        mMessage.setUserName(userName);
        return this;
    }

    public MessageBuilder messageTime(String messageTime) {
        mMessage.setMessageTime(messageTime);
        return this;
    }

    public MessageBuilder messageText(String messageText) {
        mMessage.setMessageText(messageText);
        return this;
    }

    public Message build() {
        return mMessage;
    }

}
