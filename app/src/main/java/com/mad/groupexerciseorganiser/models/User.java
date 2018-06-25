package com.mad.groupexerciseorganiser.models;

import java.io.Serializable;
import java.util.List;

/**
 * Data model related to a user object
 */


@SuppressWarnings("serial")
public class User implements Serializable {

    private String mFirstName;
    private String mLastName;
    private String mPassword;
    private String mUserType;
    private String mEmail;
    private String mUid;
    private String mStatus;
    private List<Group> mGroups;

    public User() {

    }

    public User(String firstName, String lastName, String email, String type, String uid, String status) {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mUserType = type;
        mUid = uid;
        mStatus = status;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<Group> getGroups() {
        return mGroups;
    }

    public void setGroups(List<Group> groups) {
        mGroups = groups;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
