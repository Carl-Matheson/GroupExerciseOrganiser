package com.mad.groupexerciseorganiser.players;

import com.mad.groupexerciseorganiser.models.User;

import java.util.ArrayList;

public interface IPlayerView {
    void loadUserTypeCoach();

    void loadUserTypePlayer();

    void updateRecyclerView(ArrayList<User> players);

    void removePlayer();

    void showRemovedToast(String fullName);
}
