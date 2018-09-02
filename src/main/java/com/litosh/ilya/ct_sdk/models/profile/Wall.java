package com.litosh.ilya.ct_sdk.models.profile;

import java.util.LinkedList;

/**
 * Wall сущность-коллекция содержащая
 * в себе набор сущностей Note
 *
 * @author Ilya Litosh
 */
public class Wall extends LinkedList<Note> {

    private boolean mIsContainsMorePost;

    public boolean isContainsMorePost() {
        return mIsContainsMorePost;
    }

    public void setContainsMorePost(boolean mIsContainsMorePost) {
        this.mIsContainsMorePost = mIsContainsMorePost;
    }

    @Override
    public String toString() {
        return "Contains: " + size() + " elements";
    }
}
