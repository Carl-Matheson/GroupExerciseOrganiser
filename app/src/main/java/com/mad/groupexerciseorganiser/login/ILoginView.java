package com.mad.groupexerciseorganiser.login;

public interface ILoginView {
    void loginFailed(String error);

    void loginSuccessful();

    void emptyDetails();

    void accountSetupFailure();

    void login();
}
