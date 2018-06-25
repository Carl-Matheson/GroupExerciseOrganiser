package com.mad.groupexerciseorganiser.createschedule.interactor;

public interface ICreateScheduleInteractor {
    void createSchedule(OnCreateScheduleFinishedListener listener, String name, String location, String date, String time);

    void retrieveGroupUid(String uid);
}
