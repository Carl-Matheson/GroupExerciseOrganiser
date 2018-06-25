package com.mad.groupexerciseorganiser.models;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Data model related to a group object
 */

public class Group {
    private ArrayList<User> mUsers;

    private String mGender;
    private String mName;
    private String mSport;
    private String mUri;
    private String mUid;

    public Group() {

    }

    public Group(String gender, String name, String sport, String uri) {
        mGender = gender;
        mName = name;
        mSport = sport;
        mUri = uri;
    }


    public ArrayList<User> getUsers() {
        return mUsers;
    }

    public void setUsers(ArrayList<User> users) {
        mUsers = users;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSport() {
        return mSport;
    }

    public void setSport(String sport) {
        mSport = sport;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}
