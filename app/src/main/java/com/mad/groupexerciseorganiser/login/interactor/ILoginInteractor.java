package com.mad.groupexerciseorganiser.login.interactor;

public interface ILoginInteractor {
    void validate(OnLoginFinishedListener listener, String email, String password);

    void userExists(OnLoginFinishedListener listener);
}
