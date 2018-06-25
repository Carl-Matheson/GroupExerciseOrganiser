package com.mad.groupexerciseorganiser.players.interactor;

import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

public interface PlayerListener {
    void userTypeCoach();

    void userTypePlayer();

    void updateRecyclerView(ArrayList<User> mPlayerList);

    void userRemovedToast(String fullName);
}
