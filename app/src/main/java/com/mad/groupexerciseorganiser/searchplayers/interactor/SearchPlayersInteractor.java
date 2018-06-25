package com.mad.groupexerciseorganiser.searchplayers.interactor;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;
import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

/**
 * Handles data service between Firebase and the Search Player Activity through the presenter
 */

public class SearchPlayersInteractor implements ISearchPlayersInteractor {

    private SearchPlayersListener mListener;
    private String mUid;
    private DatabaseReference mDatabase;
    private ArrayList<User> mUsersList = new ArrayList<>();
    private Context mContext;

    public SearchPlayersInteractor(SearchPlayersListener listener, String uid, Context context) {
        this.mListener = listener;
        this.mUid = uid;
        this.mContext = context;

        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users));
    }

    @Override
    public void databaseListener() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear the local instance so it doesn't create two instances of the same list
                mUsersList.clear();
                String firstName = mContext.getString(R.string.empty), email = mContext.getString(R.string.empty), lastName = mContext.getString(R.string.empty), type = mContext.getString(R.string.empty), uid, status = mContext.getString(R.string.empty);
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    uid = user.getKey();
                    for (DataSnapshot child : user.getChildren()) {
                        if (child.getKey().equals(mContext.getString(R.string.firstname))) {
                            firstName = child.getValue(String.class);
                        } else if (child.getKey().equals(mContext.getString(R.string.lastName))) {
                            lastName = child.getValue(String.class);
                        } else if (child.getKey().equals(mContext.getString(R.string.emaildb))) {
                            email = child.getValue(String.class);
                        } else if (child.getKey().equals(mContext.getString(R.string.typedb))) {
                            type = child.getValue(String.class);
                        } else if (child.getKey().equals(mContext.getString(R.string.groups))) {
                            for (DataSnapshot userStatus : child.getChildren()) {
                                status = userStatus.getValue(String.class);
                            }
                        }
                    }
                    if (type.equals(mContext.getString(R.string.player))) {
                        mUsersList.add(new User(firstName, lastName, email, type, uid, status));
                    }
                }
                mListener.updateRecyclerView(mUsersList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError.getMessage());
            }
        });
    }

    @Override
    public void invitePlayer(int position) {
        User user = mUsersList.get(position);
        String uid = user.getUid();
        if (!user.getStatus().equals("invited")) {
            mListener.userInvitedToast(user.getFullName());

            //Add a list of players in the group
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups)).child(mUid);
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.statusdb)).setValue(mContext.getString(R.string.inviteddb));
            user.setStatus(mContext.getString(R.string.inviteddb));
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.firstName)).setValue(user.getFirstName());
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.lastName)).setValue(user.getLastName());
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.emaildb)).setValue(user.getEmail());
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.typedb)).setValue(user.getUserType());
            db.child(mContext.getString(R.string.playersdb)).child(uid).child(mContext.getString(R.string.uid)).setValue(user.getUid());

            DatabaseReference groupDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users)).child(uid);
            groupDatabase.child(mContext.getString(R.string.groups)).child(mUid).setValue(mContext.getString(R.string.inviteddb));
        } else {
            mListener.userAlreadyInvitedToast(user.getFullName());
        }
        mListener.updateRecyclerView(mUsersList);
    }
}
