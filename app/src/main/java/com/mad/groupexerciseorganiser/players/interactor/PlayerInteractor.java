package com.mad.groupexerciseorganiser.players.interactor;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

/**
 * Handles data service between Firebase and the PlayerActivity through the presenter
 */


public class PlayerInteractor implements IPlayerInteractor {

    private PlayerListener mListener;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String mUid;
    private ArrayList<User> mPlayerList = new ArrayList<>();
    private Context mContext;

    public PlayerInteractor(PlayerListener listener, String uid, Context context) {
        this.mListener = listener;
        this.mContext = context;
        mUid = uid;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups)).child(uid).child(mContext.getString(R.string.playersdb));
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users)).child(mAuth.getCurrentUser().getUid());

    }

    @Override
    public void databaseListener() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear the local instance so it doesn't create two instances of the same list
                mPlayerList.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mPlayerList.add(child.getValue(User.class));
                }
                mListener.updateRecyclerView(mPlayerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(mContext.getString(R.string.readFailed_prompt) + databaseError.getCode());
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

    @Override
    public void removePlayer(int position) {
        User user = mPlayerList.get(position);
        mListener.userRemovedToast(user.getFullName());
        mPlayerList.remove(position);
        mDatabase.child(user.getUid()).removeValue();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users)).child(user.getUid()).child(mContext.getString(R.string.groups)).child(mUid);
        db.removeValue();
    }
}
