package com.mad.groupexerciseorganiser.schedule.interactor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Schedule;

import java.util.ArrayList;

/**
 * Handles data service between Firebase and the Schedule Activity through the presenter
 */


public class ScheduleInteractor implements IScheduleInteractor {

    private ScheduleListener mListener;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private ArrayList<Schedule> mSchedules = new ArrayList<>();
    private Context mContext;

    public ScheduleInteractor(ScheduleListener listener, String uid, Context context) {
        this.mListener = listener;
        this.mContext = context;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups)).child(uid).child(mContext.getString(R.string.schedules));
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.users)).child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public void databaseListener() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear the local instance so it doesn't create two instances of the same list
                mSchedules.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mSchedules.add(child.getValue(Schedule.class));
                }
                mListener.updateRecyclerView(mSchedules);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(mContext.getString(R.string.read_failed) + databaseError.getCode());
            }
        });

        /**
         * Checks the type of current user, to display correct ui elements
         */
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getKey().equals(mContext.getString(R.string.typedb))) {
                        String type = child.getValue(String.class);
                        if (type.equals(mContext.getString(R.string.coach))) {
                            mListener.userTypeCoach();
                        } else {
                            mListener.userTypePlayer();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(mContext.getString(R.string.read_failed) + databaseError.getCode());
            }
        });
    }
}
