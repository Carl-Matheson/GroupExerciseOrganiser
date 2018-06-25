package com.mad.groupexerciseorganiser.login.interactor;

public interface OnLoginFinishedListener {
    void onError(String error);

    void onEmptyDetails();

    void onAccountSetupFailure();

    void onSuccess();
}
