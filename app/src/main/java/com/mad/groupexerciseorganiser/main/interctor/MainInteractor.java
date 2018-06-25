package com.mad.groupexerciseorganiser.main.interctor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.Group;

import java.util.ArrayList;

/**
 * Interactor class that handles data related to the main activity
 */

public class MainInteractor implements IMainInteractor {

    private DatabaseReference mUserDatabase;
    private DatabaseReference mGroupDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MainListener mListener;
    private Context mContext;

    private ArrayList<Group> mUserGroups = new ArrayList<>();
    private ArrayList<String> mUidList = new ArrayList<>();
    private String mUserType;

    public MainInteractor(MainListener listener, Context context) {
        this.mListener = listener;
        mAuth = FirebaseAuth.getInstance();
        mListener.updateRecyclerView(mUserGroups);
        this.mContext = context;
    }

    /**
     * Called to check if user exists in database (not just fire auth)
     */
    @Override
    public void userState() {
        //populates local database reference is user is currently logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = mAuth.getCurrentUser().getUid();
            mListener.userLoggedIn();
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users)).child(uid);
            mGroupDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups));
        } else {
            mListener.showLoginActivity();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    mListener.showLoginActivity();
                }
            }
        };
    }

    /**
     * Setups event listeners to the database to retrieve data
     */
    @Override
    public void databaseListener() {
        try {
            /**
             * Checks the type of current user, to display correct ui elements
             */
            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals(mContext.getResources().getString(R.string.typedb))) {
                            mUserType = child.getValue(String.class);
                            if (mUserType.equals(mContext.getString(R.string.coach))) {
                                mListener.loadUserTypeOrganiser();
                            } else {
                                mListener.loadUserTypeParticipant();
                            }
                        }
                        if (child.getKey().equals(mContext.getString(R.string.groups))) {
                            for (DataSnapshot group : child.getChildren()) {
                                mUidList.add(group.getKey());
                            }
                        }
                    }
                    callGroupDatabase();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println(mContext.getString(R.string.read_failed) + databaseError.getCode());
                }
            });

        } catch (NullPointerException e) {
            mListener.showLoginActivity();
        }
    }

    /**
     * Loads all group data and displays it on main
     */
    private void callGroupDatabase() {
        mGroupDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                mUserGroups.clear();
                for (DataSnapshot groups : dataSnapshot.getChildren()) {
                    //check if key of group exists in user
                    for (String uid : mUidList) {
                        if (groups.getKey().equals(uid)) {
                            mUserGroups.add(groups.getValue(Group.class));
                            Group currentGroup = mUserGroups.get(count);
                            currentGroup.setUid(groups.getKey());
                            count++;
                        }
                    }
                }
                mListener.updateRecyclerView(mUserGroups);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(mContext.getString(R.string.read_failed) + databaseError.getCode());
            }
        });
    }

    @Override
    public Group getGroupData(int position) {
        return mUserGroups.get(position);
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }

    @Override
    public void addAuthStateListener() {
        mAuth.addAuthStateListener(mAuthListener);
    }

}
