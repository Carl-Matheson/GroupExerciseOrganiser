package com.mad.groupexerciseorganiser.searchplayers.interactor;

import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

public interface SearchPlayersListener {
    void updateRecyclerView(ArrayList<User> mPlayerList);

    void userInvitedToast(String fullName);

    void userAlreadyInvitedToast(String fullName);
}
