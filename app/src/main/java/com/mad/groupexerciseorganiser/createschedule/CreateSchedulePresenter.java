package com.mad.groupexerciseorganiser.createschedule;

import android.content.Context;

import com.mad.groupexerciseorganiser.createschedule.interactor.CreateScheduleInteractor;
import com.mad.groupexerciseorganiser.createschedule.interactor.OnCreateScheduleFinishedListener;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class CreateSchedulePresenter implements ICreateSchedulePresenter, OnCreateScheduleFinishedListener {

    private ICreateScheduleView mView;
    private CreateScheduleInteractor mInteractor;

    public CreateSchedulePresenter(ICreateScheduleView view, Context context) {
        this.mView = view;
        mInteractor = new CreateScheduleInteractor(context);
    }

    @Override
    public void createSchedule(String name, String location, String date, String time) {
        mInteractor.createSchedule(this, name, location, date, time);
    }

    @Override
    public void passGroupUid(String uid) {
        mInteractor.retrieveGroupUid(uid);
    }

    @Override
    public void onEmptyDetails() {
        mView.emptyDetails();
    }

    @Override
    public void onSuccess() {
        mView.scheduleSuccessfullyCreated();
    }

    @Override
    public void onError(String error) {
        mView.error(error);
    }
}
