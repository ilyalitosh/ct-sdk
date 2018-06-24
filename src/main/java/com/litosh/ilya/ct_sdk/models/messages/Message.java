package com.litosh.ilya.ct_sdk.models.messages;

/**
 * Message модель с данными одного сообщения
 *
 * Created by ilya_ on 24.06.2018.
 */

public class Message {

    private String mUserName;
    private String mMessageTime;
    private String mMessageText;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getMessageTime() {
        return mMessageTime;
    }

    public void setMessageTime(String mMessageTime) {
        this.mMessageTime = mMessageTime;
    }

    public String getMessageText() {
        return mMessageText;
    }

    public void setMessageText(String mMessageText) {
        this.mMessageText = mMessageText;
    }
}
