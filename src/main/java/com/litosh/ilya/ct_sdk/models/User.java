package com.litosh.ilya.ct_sdk.models;

/**
 * User модель
 *
 * Created by ilya_ on 21.06.2018.
 */

public class User {

    private String mProfileName;
    private String mCountry;
    private String mCity;
    private String mSex;
    private String mWca;

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
}
