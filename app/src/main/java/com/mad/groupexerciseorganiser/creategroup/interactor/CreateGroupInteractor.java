package com.mad.groupexerciseorganiser.creategroup.interactor;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mad.groupexerciseorganiser.R;

/**
 * Handles data service between Firebase and the CreateGroupActivity through the presenter
 */


public class CreateGroupInteractor implements ICreateGroupInteractor {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private String mGroupUid;
    private Context mContext;

    public CreateGroupInteractor(Context context) {
        mAuth = FirebaseAuth.getInstance();
        this.mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users));
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    /**
     * Handles input validation and creation of groups on the firebase database
     */
    @Override
    public void validateGroup(final OnCreateGroupFinishedListener listener, final String name, final String gender, final String sport, final Uri uri) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sport) && !TextUtils.isEmpty(gender) && uri != null) {
            String uid = mAuth.getCurrentUser().getUid();
            mDatabase = mDatabase.child(uid);

            //generates a random key for each group in groups
            mGroupUid = mDatabase.child(mContext.getString(R.string.groups)).push().getKey();

            //create a child of groups with uid for each group
            mDatabase.child(mContext.getString(R.string.groups)).child(mGroupUid).setValue(mContext.getString(R.string.uid));

            //Creates an image reference for each group that exists
            StorageReference imagePath = mStorage.child(mContext.getString(R.string.images)).child(mGroupUid + mContext.getString(R.string.jpg));
            imagePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult().getDownloadUrl();

                        DatabaseReference groups = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.groups));
                        groups.child(mGroupUid).child(mContext.getString(R.string.namedb)).setValue(name);
                        groups.child(mGroupUid).child(mContext.getString(R.string.sportdb)).setValue(sport);
                        groups.child(mGroupUid).child(mContext.getString(R.string.genderdb)).setValue(gender);
                        groups.child(mGroupUid).child(mContext.getString(R.string.uridb)).setValue(downloadUrl.toString());

                        listener.onSuccess();
                    } else {
                        String error = task.getException().getMessage();
                        listener.onError(error);
                    }
                }
            });
        } else {
            listener.onEmptyDetails();
        }
    }
}
