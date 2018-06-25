package com.mad.groupexerciseorganiser.signup;

/**
 * Created by carlmatheson on 17/5/18.
 */

public interface ISignUpView {
    void signUpUser();

    void signUpFailed(String error);

    void signUpSuccessful();

    void emptyDetails();
}
