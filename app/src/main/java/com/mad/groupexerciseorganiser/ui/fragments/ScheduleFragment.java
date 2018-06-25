package com.mad.groupexerciseorganiser.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.adapter.ScheduleAdapter;
import com.mad.groupexerciseorganiser.main.MainPresenter;
import com.mad.groupexerciseorganiser.main.RecyclerTouchListener;
import com.mad.groupexerciseorganiser.models.Schedule;
import com.mad.groupexerciseorganiser.schedule.IScheduleView;
import com.mad.groupexerciseorganiser.schedule.SchedulePresenter;
import com.mad.groupexerciseorganiser.ui.CreateScheduleActivity;

import java.util.ArrayList;

/**
 * Fragment that Handles all view related components to schedules
 */

public class ScheduleFragment extends Fragment implements IScheduleView {

    private String mUid;
    private String mName;
    ScheduleAdapter mAdapter;
    SchedulePresenter mPresenter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mRecyclerView = view.findViewById(R.id.content_schedule_recyclerView);

        mUid = getArguments().getString(getString(R.string.uid));
        mName = getArguments().getString(getString(R.string.name));

        mPresenter = new SchedulePresenter(this, mUid, getContext());
        mPresenter.callDatabase();

        mFloatingActionButton = view.findViewById(R.id.schedule_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleIntent = new Intent(view.getContext(), CreateScheduleActivity.class);
                scheduleIntent.putExtra(getString(R.string.uid), mUid);
                scheduleIntent.putExtra(getString(R.string.name), mName);
                startActivity(scheduleIntent);
            }
        });

        return view;
    }

    public void loadUserTypeCoach() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    public void loadUserTypePlayer() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
    }

    public void updateRecyclerView(ArrayList<Schedule> schedules) {
        mAdapter = new ScheduleAdapter(schedules, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
}

