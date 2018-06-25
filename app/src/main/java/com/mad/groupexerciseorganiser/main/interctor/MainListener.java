package com.mad.groupexerciseorganiser.main.interctor;

import com.mad.groupexerciseorganiser.models.Group;

import java.util.ArrayList;

public interface MainListener {
    void userLoggedIn();

    void showLoginActivity();

    void loadUserTypeOrganiser();

    void loadUserTypeParticipant();

    void updateRecyclerView(ArrayList<Group> groups);
}
