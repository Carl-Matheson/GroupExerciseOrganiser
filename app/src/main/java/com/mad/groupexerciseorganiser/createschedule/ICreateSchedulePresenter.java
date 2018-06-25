package com.mad.groupexerciseorganiser.createschedule;

public interface ICreateSchedulePresenter {
    void createSchedule(String name, String location, String date, String time);

    void onEmptyDetails();

    void onSuccess();

    void onError(String error);

    void passGroupUid(String uid);
}
