package com.mad.groupexerciseorganiser.schedule;

import android.content.Context;

import com.mad.groupexerciseorganiser.models.Schedule;
import com.mad.groupexerciseorganiser.schedule.interactor.ScheduleInteractor;
import com.mad.groupexerciseorganiser.schedule.interactor.ScheduleListener;

import java.util.ArrayList;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class SchedulePresenter implements ISchedulePresenter, ScheduleListener {
    private IScheduleView mView;
    private ScheduleInteractor mInteractor;

    public SchedulePresenter(IScheduleView view, String uid, Context context) {
        this.mView = view;
        mInteractor = new ScheduleInteractor(this, uid, context);
    }

    @Override
    public void userTypeCoach() {
        mView.loadUserTypeCoach();
    }

    @Override
    public void userTypePlayer() {
        mView.loadUserTypePlayer();
    }

    @Override
    public void updateRecyclerView(ArrayList<Schedule> schedules) {
        mView.updateRecyclerView(schedules);
    }

    @Override
    public void callDatabase() {
        mInteractor.databaseListener();
    }

}
