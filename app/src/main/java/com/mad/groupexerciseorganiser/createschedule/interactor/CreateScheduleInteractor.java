package com.mad.groupexerciseorganiser.createschedule.interactor;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.ui.CreateScheduleActivity;
import com.mad.groupexerciseorganiser.ui.GroupActivity;

/**
 * Handles data service between Firebase and the CreateScheduleActivity through the presenter
 */

public class CreateScheduleInteractor implements ICreateScheduleInteractor {

    private String mUid;
    private DatabaseReference mDatabase;
    private Context mContext;

    public CreateScheduleInteractor(Context context) {
        mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups));
    }

    @Override
    public void retrieveGroupUid(String uid) {
        mUid = uid;
    }

    @Override
    public void createSchedule(OnCreateScheduleFinishedListener listener, String name, String location, String date, String time) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(date)) {
            //generates a random key for each schedule in schedules
            String uid = mDatabase.push().getKey();

            mDatabase = mDatabase.child(mUid).child(mContext.getString(R.string.schedules));

            //create a child of groups with uid for each group
            mDatabase.child(uid).child(mContext.getString(R.string.namedb)).setValue(name);
            mDatabase.child(uid).child(mContext.getString(R.string.datedb)).setValue(date);
            mDatabase.child(uid).child(mContext.getString(R.string.timedb)).setValue(time);
            mDatabase.child(uid).child(mContext.getString(R.string.locationdb)).setValue(location);
            mDatabase.child(uid).child(mContext.getString(R.string.uiddb)).setValue(uid);

            listener.onSuccess();
        } else {
            listener.onEmptyDetails();
        }
    }
}
