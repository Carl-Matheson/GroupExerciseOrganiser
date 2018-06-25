package com.mad.groupexerciseorganiser.players;

import android.content.Context;

import com.mad.groupexerciseorganiser.models.User;
import com.mad.groupexerciseorganiser.players.interactor.PlayerInteractor;
import com.mad.groupexerciseorganiser.players.interactor.PlayerListener;

import java.util.ArrayList;

/**
 * Handles the calls between Data and View, allows for Concern Separation
 */

public class PlayerPresenter implements IPlayerPresenter, PlayerListener {

    private IPlayerView mView;
    private PlayerInteractor mInteractor;

    public PlayerPresenter(IPlayerView view, String uid, Context context) {
        this.mView = view;
        mInteractor = new PlayerInteractor(this, uid, context);
    }

    @Override
    public void userRemovedToast(String fullName) {
        mView.showRemovedToast(fullName);
    }

    @Override
    public void userTypeCoach() {
        mView.loadUserTypeCoach();
    }

    @Override
    public void userTypePlayer() {
        mView.loadUserTypePlayer();
    }

    @Override
    public void updateRecyclerView(ArrayList<User> players) {
        mView.updateRecyclerView(players);
    }

    @Override
    public void callDatabase() {
        mInteractor.databaseListener();
    }

    @Override
    public void removeUser(int position) {
        mInteractor.removePlayer(position);
    }
}
