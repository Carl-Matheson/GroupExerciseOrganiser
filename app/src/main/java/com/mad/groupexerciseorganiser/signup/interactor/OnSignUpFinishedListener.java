package com.mad.groupexerciseorganiser.signup.interactor;

/**
 * Created by carlmatheson on 17/5/18.
 */

public interface OnSignUpFinishedListener {
    void onError(String error);

    void onEmptyDetails();

    void onSuccess();
}
