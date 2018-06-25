package com.mad.groupexerciseorganiser.schedule.interactor;

import com.mad.groupexerciseorganiser.models.Schedule;

import java.util.ArrayList;

public interface ScheduleListener {
    void userTypeCoach();

    void userTypePlayer();

    void updateRecyclerView(ArrayList<Schedule> schedules);
}
