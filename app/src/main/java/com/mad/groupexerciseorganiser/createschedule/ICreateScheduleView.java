package com.mad.groupexerciseorganiser.createschedule;

public interface ICreateScheduleView {
    void createNewSchedule();

    void scheduleSuccessfullyCreated();

    void emptyDetails();

    void error(String error);
}
