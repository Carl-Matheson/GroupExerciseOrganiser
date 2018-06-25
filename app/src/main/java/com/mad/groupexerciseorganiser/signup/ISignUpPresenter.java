package com.mad.groupexerciseorganiser.signup;

/**
 * Created by carlmatheson on 17/5/18.
 */

public interface ISignUpPresenter {
    void validateSignUp(String firstName, String lastName, String email, String password, String type);
}
