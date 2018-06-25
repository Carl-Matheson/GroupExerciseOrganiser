package com.mad.groupexerciseorganiser.creategroup.interactor;

/**
 * Basic interface for the presenter and interactor
 */

public interface OnCreateGroupFinishedListener {
    void onEmptyDetails();

    void onSuccess();

    void onError(String error);
}