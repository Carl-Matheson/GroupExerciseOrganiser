package com.mad.groupexerciseorganiser.login;

import android.content.Context;

import com.mad.groupexerciseorganiser.login.interactor.LoginInteractor;
import com.mad.groupexerciseorganiser.login.interactor.OnLoginFinishedListener;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class LoginPresenter implements ILoginPresenter, OnLoginFinishedListener {

    private ILoginView mView;
    private LoginInteractor mInterator;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.mView = loginView;
        //Interactor acts as the model handling all data
        this.mInterator = new LoginInteractor(context);
    }

    @Override
    public void validateLogin(String email, String password) {
        mInterator.validate(this, email, password);
    }

    @Override
    public void onError(String error) {
        mView.loginFailed(error);
    }

    @Override
    public void onSuccess() {
        mView.loginSuccessful();
    }

    @Override
    public void onEmptyDetails() {
        mView.emptyDetails();
    }

    @Override
    public void onAccountSetupFailure() {
        mView.accountSetupFailure();
    }
}
