package com.litosh.ilya.ct_sdk.models.messages;

/**
 * Chat модель с данными одного
 * чата из списка чатов
 *
 * Created by ilya_ on 24.06.2018.
 */

public class Chat {

    private String mChatId;
    private String mUrlChatImage;
    private String mChatName;
    private String mChatTime;
    private String mChatLastMessage;
    private boolean mIsContainsNewMessage;

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String mChatId) {
        this.mChatId = mChatId;
    }

    public String getUrlChatImage() {
        return mUrlChatImage;
    }

    public void setUrlChatImage(String mUrlChatImage) {
        this.mUrlChatImage = mUrlChatImage;
    }

    public String getChatName() {
        return mChatName;
    }

    public void setChatName(String mChatName) {
        this.mChatName = mChatName;
    }

    public String getChatTime() {
        return mChatTime;
    }

    public void setChatTime(String mChatTime) {
        this.mChatTime = mChatTime;
    }

    public String getChatLastMessage() {
        return mChatLastMessage;
    }

    public void setChatLastMessage(String mChatLastMessage) {
        this.mChatLastMessage = mChatLastMessage;
    }

    public boolean isContainsNewMessage() {
        return mIsContainsNewMessage;
    }

    public void setContainsNewMessage(boolean mIsContainsNewMessage) {
        this.mIsContainsNewMessage = mIsContainsNewMessage;
    }

}
