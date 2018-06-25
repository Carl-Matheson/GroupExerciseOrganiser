package com.mad.groupexerciseorganiser.creategroup;

import android.content.Context;
import android.net.Uri;

import com.mad.groupexerciseorganiser.creategroup.interactor.CreateGroupInteractor;
import com.mad.groupexerciseorganiser.creategroup.interactor.OnCreateGroupFinishedListener;

/**
 * Handles the calls between Data and View, allows for Concern Seperation
 */

public class CreateGroupPresenter implements ICreateGroupPresenter, OnCreateGroupFinishedListener {

    private ICreateGroupView mView;
    private CreateGroupInteractor mInterator;

    public CreateGroupPresenter(ICreateGroupView view, Context context) {
        this.mView = view;
        this.mInterator = new CreateGroupInteractor(context);
    }

    @Override
    public void validateGroup(String name, String gender, String sport, Uri uri) {
        mInterator.validateGroup(this, name, gender, sport, uri);
    }

    @Override
    public void onError(String error) {
        mView.error(error);
    }

    @Override
    public void onEmptyDetails() {
        mView.emptyDetails();
    }

    @Override
    public void onSuccess() {
        mView.groupSuccessfullyCreated();
    }

}
