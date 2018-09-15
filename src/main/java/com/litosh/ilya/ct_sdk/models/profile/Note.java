package com.litosh.ilya.ct_sdk.models.profile;

/**
 * Note сущность одной записи на стене
 *
 * @author Ilya Litosh
 */
public class Note {

    private long mNoteId;
    private String mUserName;
    private boolean mIsUserOnline;
    private String mUserId;
    private String mUrlUserAvatar;
    private String mDate;
    private int mLikesNumber;
    private String mText;
    private int mCommentsNumber;
    private boolean isLikedMe;

    public long getNoteId() {
        return mNoteId;
    }

    public void setNoteId(long mNoteId) {
        this.mNoteId = mNoteId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public boolean isUserOnline() {
        return mIsUserOnline;
    }

    public void setUserOnline(boolean mIsUserOnline) {
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

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getCommentsNumber() {
        return mCommentsNumber;
    }

    public void setCommentsNumber(int mCommentsNumber) {
        this.mCommentsNumber = mCommentsNumber;
    }

    public boolean isLikedMe() {
        return isLikedMe;
    }

    public void setLikedMe(boolean likedMe) {
        isLikedMe = likedMe;
    }
}
