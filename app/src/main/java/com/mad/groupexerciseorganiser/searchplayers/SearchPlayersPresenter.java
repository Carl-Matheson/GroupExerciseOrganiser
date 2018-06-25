package com.mad.groupexerciseorganiser.searchplayers;

import android.content.Context;

import com.mad.groupexerciseorganiser.models.User;
import com.mad.groupexerciseorganiser.searchplayers.interactor.ISearchPlayersInteractor;
import com.mad.groupexerciseorganiser.searchplayers.interactor.SearchPlayersInteractor;
import com.mad.groupexerciseorganiser.searchplayers.interactor.SearchPlayersListener;

import java.util.ArrayList;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class SearchPlayersPresenter implements ISearchPlayersPresenter, SearchPlayersListener {

    private ISearchPlayersView mView;
    private ISearchPlayersInteractor mInteractor;

    public SearchPlayersPresenter(ISearchPlayersView view, String uid, Context context) {
        this.mView = view;
        mInteractor = new SearchPlayersInteractor(this, uid, context);
    }

    @Override
    public void userInvitedToast(String fullName) {
        mView.showInvitedToast(fullName);
    }

    @Override
    public void userAlreadyInvitedToast(String fullName) {
        mView.showAlreadyInvitedToast(fullName);
    }

    @Override
    public void updateRecyclerView(ArrayList<User> users) {
        mView.updateRecyclerView(users);
    }

    @Override
    public void callDatabaseListener() {
        mInteractor.databaseListener();
    }

    @Override
    public void invitePlayer(int position) {
        mInteractor.invitePlayer(position);
    }
}
