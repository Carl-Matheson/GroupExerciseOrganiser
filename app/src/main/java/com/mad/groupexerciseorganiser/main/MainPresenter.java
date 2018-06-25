package com.mad.groupexerciseorganiser.main;

import android.content.Context;

import com.mad.groupexerciseorganiser.main.interctor.IMainInteractor;
import com.mad.groupexerciseorganiser.main.interctor.MainInteractor;
import com.mad.groupexerciseorganiser.main.interctor.MainListener;
import com.mad.groupexerciseorganiser.models.Group;

import java.util.ArrayList;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class MainPresenter implements IMainPresenter, MainListener {

    private IMainView mView;
    private IMainInteractor mInterator;

    public MainPresenter(IMainView view, Context context) {
        this.mView = view;
        this.mInterator = new MainInteractor(this, context);
    }

    @Override
    public void checkUserState() {
        mInterator.userState();
    }

    @Override
    public void callDatabaseListener() {
        mInterator.databaseListener();
    }

    @Override
    public void logout() {
        mInterator.logout();
    }

    @Override
    public void addAuthStateListener() {
        mInterator.addAuthStateListener();
    }

    @Override
    public Group handleGroupClick(int position) {
        return mInterator.getGroupData(position);
    }

    @Override
    public void updateRecyclerView(ArrayList<Group> groups) {
        mView.updateRecyclerView(groups);
    }

    @Override
    public void loadUserTypeOrganiser() {
        mView.loadUserTypeOrganiser();
    }

    @Override
    public void loadUserTypeParticipant() {
        mView.loadUserTypeParticipant();
    }

    @Override
    public void showLoginActivity() {
        mView.startLoginActivity();
    }

    @Override
    public void userLoggedIn() {

    }

}
