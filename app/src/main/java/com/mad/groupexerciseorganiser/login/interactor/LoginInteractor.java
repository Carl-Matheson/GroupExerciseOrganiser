package com.mad.groupexerciseorganiser.login.interactor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.groupexerciseorganiser.R;

/**
 * Model that handles all relevant login data passed in via the presenter
 */

public class LoginInteractor implements ILoginInteractor {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public LoginInteractor(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(context.getResources().getString(R.string.users));
    }

    @Override
    public void validate(final OnLoginFinishedListener listener, final String email, final String password) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userExists(listener);
                        listener.onSuccess();
                    } else {
                        listener.onError(task.getException().getMessage());
                    }
                }
            });
        } else {
            listener.onEmptyDetails();
        }
    }

    @Override
    public void userExists(final OnLoginFinishedListener listener) {
        final String uid = mAuth.getCurrentUser().getUid();

        //Check if the user exists in the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)) {
                    listener.onSuccess();
                } else {
                    listener.onAccountSetupFailure();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print(databaseError.getMessage());
            }
        });
    }
}
