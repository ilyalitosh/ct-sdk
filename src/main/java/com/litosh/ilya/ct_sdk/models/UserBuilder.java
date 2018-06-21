package com.litosh.ilya.ct_sdk.models;

/**
 * UserBuilder для создания User
 *
 * Created by ilya_ on 21.06.2018.
 */

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder profileName(String profileName) {
        user.setProfileName(profileName);
        return this;
    }

    public UserBuilder country(String country) {
        user.setCountry(country);
        return this;
    }

    public UserBuilder city(String city) {
        user.setCity(city);
        return this;
    }

    public UserBuilder sex(String sex) {
        user.setSex(sex);
        return this;
    }

    public UserBuilder wca(String wca) {
        user.setWca(wca);
        return this;
    }

    public User build() {
        return user;
    }

}
