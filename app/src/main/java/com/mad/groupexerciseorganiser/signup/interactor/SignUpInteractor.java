package com.mad.groupexerciseorganiser.signup.interactor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.groupexerciseorganiser.R;

/**
 * Handles data service between Firebase and the Sign-up Activity through the presenter
 */

public class SignUpInteractor implements ISignUpInteractor {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Context mContext;

    public SignUpInteractor(Context context) {
        mAuth = FirebaseAuth.getInstance();
        this.mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.users));
    }

    @Override
    public void validate(final OnSignUpFinishedListener listener, final String firstName, final String lastName, final String email, final String password, final String type) {
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(type)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        DatabaseReference user_db = mDatabase.child(uid);

                        user_db.child(mContext.getString(R.string.firstName)).setValue(firstName);
                        user_db.child(mContext.getString(R.string.lastName)).setValue(lastName);
                        user_db.child(mContext.getString(R.string.dbemail)).setValue(email);
                        user_db.child(mContext.getString(R.string.dbpassword)).setValue(password);
                        user_db.child(mContext.getString(R.string.typedb)).setValue(type);
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
}
