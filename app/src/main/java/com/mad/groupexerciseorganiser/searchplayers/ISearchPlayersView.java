package com.mad.groupexerciseorganiser.searchplayers;

import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

public interface ISearchPlayersView {
    void showInvitedToast(String fullName);

    void showAlreadyInvitedToast(String fullName);

    void updateRecyclerView(ArrayList<User> users);
}
