package com.mad.groupexerciseorganiser.schedule;

import android.view.View;

import com.mad.groupexerciseorganiser.models.Schedule;

import java.util.ArrayList;

public interface IScheduleView {
    void loadUserTypeCoach();

    void loadUserTypePlayer();

    void updateRecyclerView(ArrayList<Schedule> schedules);
}
