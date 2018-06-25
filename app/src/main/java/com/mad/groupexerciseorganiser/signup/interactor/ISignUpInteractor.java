package com.mad.groupexerciseorganiser.signup.interactor;

/**
 * Created by carlmatheson on 17/5/18.
 */

public interface ISignUpInteractor {
    void validate(OnSignUpFinishedListener listener, String firstName, String lastName, String email, String password, String type);
}
