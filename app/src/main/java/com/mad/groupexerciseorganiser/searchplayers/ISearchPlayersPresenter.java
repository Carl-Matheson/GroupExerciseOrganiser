package com.mad.groupexerciseorganiser.searchplayers;

import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

public interface ISearchPlayersPresenter {
    void updateRecyclerView(ArrayList<User> users);

    void callDatabaseListener();

    void invitePlayer(int position);
}
