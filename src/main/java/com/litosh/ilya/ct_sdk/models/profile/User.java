package com.litosh.ilya.ct_sdk.models.profile;

/**
 * User модель
 *
 * @author Ilya Litosh
 */

public class User {

    private String mProfileName;
    private String mCountry;
    private String mCity;
    private String mSex;
    private String mWca;
    private String mActivity;
    private String mFriendsCount;
    private String mUrlAvatar;

    public String getProfileName() {
        return mProfileName;
    }

    public void setProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String mSex) {
        this.mSex = mSex;
    }

    public String getWca() {
        return mWca;
    }

    public void setWca(String mWca) {
        this.mWca = mWca;
    }

    public String getActivity() {
        return mActivity;
    }

    public void setActivity(String mActivity) {
        this.mActivity = mActivity;
    }

    public String getFriendsCount() {
        return mFriendsCount;
    }

    public void setFriendsCount(String mFriendsCount) {
        this.mFriendsCount = mFriendsCount;
    }

    public String getUrlAvatar() {
        return mUrlAvatar;
    }

    public void setUrlAvatar(String mUrlAvatar) {
        this.mUrlAvatar = mUrlAvatar;
    }
}
