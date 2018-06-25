package com.mad.groupexerciseorganiser.signup;

import android.content.Context;

import com.mad.groupexerciseorganiser.signup.interactor.SignUpInteractor;
import com.mad.groupexerciseorganiser.signup.interactor.OnSignUpFinishedListener;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class SignUpPresenter implements ISignUpPresenter, OnSignUpFinishedListener {

    private ISignUpView mView;
    private SignUpInteractor mInterator;

    public SignUpPresenter(ISignUpView view, Context context) {
        this.mView = view;
        //Interactor acts as the model handling all data
        this.mInterator = new SignUpInteractor(context);
    }

    @Override
    public void validateSignUp(String firstName, String lastName, String email, String password, String type) {
        mInterator.validate(this, firstName, lastName, email, password, type);
    }

    @Override
    public void onError(String error) {
        mView.signUpFailed(error);
    }

    @Override
    public void onEmptyDetails() {
        mView.emptyDetails();
    }

    @Override
    public void onSuccess() {
        mView.signUpSuccessful();
    }
}
