package com.mad.groupexerciseorganiser.models;

/**
 * Data model related to a schedule object
 */

public class Schedule {

    private String mName;
    private String mDate;
    private String mTime;
    private String mLocation;
    private String mUid;

    public Schedule() {

    }

    public Schedule(String name, String date, String time, String location, String uid) {
        mName = name;
        mDate = date;
        mTime = time;
        mLocation = location;
        mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}
