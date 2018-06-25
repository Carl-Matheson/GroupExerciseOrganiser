package com.mad.groupexerciseorganiser.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Group;
import com.mad.groupexerciseorganiser.models.Schedule;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Adapter handles underlying data for recycler views
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    //Declaration
    private ArrayList<Schedule> mScheduleList;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        //Declaration
        @BindView(R.id.schedule_item_name)
        TextView mName;
        @BindView(R.id.schedule_item_location)
        TextView mLocation;
        @BindView(R.id.schedule_item_time)
        TextView mTime;
        @BindView(R.id.schedule_item_date)
        TextView mDate;

        public ViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.schedule_item_name);
            mLocation = view.findViewById(R.id.schedule_item_location);
            mTime = view.findViewById(R.id.schedule_item_time);
            mDate = view.findViewById(R.id.schedule_item_date);
        }
    }

    public ScheduleAdapter(ArrayList<Schedule> schedules, Context context) {
        mScheduleList = schedules;
        mContext = context;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule schedule = mScheduleList.get(position);
        holder.mName.setText(schedule.getName());
        holder.mDate.setText(schedule.getDate());
        holder.mTime.setText(schedule.getTime());
        holder.mLocation.setText(schedule.getLocation());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }
}
