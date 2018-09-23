package com.litosh.ilya.ct_sdk.models.profile;

import java.io.Serializable;

/**
 * Comment
 * Модель комментария сериализуемая
 * @author Ilya Litosh
 */
public class Comment implements Serializable {

    private long mCommentId;
    private String mUserName;
    private boolean mIsUserOnline;
    private String mUserId;
    private String mUrlUserAvatar;
    private String mDate;
    private int mLikesNumber;
    private String mCommentText;
    private boolean isLikedMe;

    public long getCommentId() {
        return mCommentId;
    }

    public void setCommentId(long mCommentId) {
        this.mCommentId = mCommentId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public boolean isIsUserOnline() {
        return mIsUserOnline;
    }

    public void setIsUserOnline(boolean mIsUserOnline) {
        this.mIsUserOnline = mIsUserOnline;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getUrlUserAvatar() {
        return mUrlUserAvatar;
    }

    public void setUrlUserAvatar(String mUrlUserAvatar) {
        this.mUrlUserAvatar = mUrlUserAvatar;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public int getLikesNumber() {
        return mLikesNumber;
    }

    public void setLikesNumber(int mLikesNumber) {
        this.mLikesNumber = mLikesNumber;
    }

    public String getCommentText() {
        return mCommentText;
    }

    public void setCommentText(String mText) {
        this.mCommentText = mText;
    }

    public boolean isLikedMe() {
        return isLikedMe;
    }

    public void setLikedMe(boolean likedMe) {
        isLikedMe = likedMe;
    }
}
