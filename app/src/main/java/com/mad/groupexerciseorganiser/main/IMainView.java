package com.mad.groupexerciseorganiser.main;

import com.mad.groupexerciseorganiser.models.Group;

import java.util.ArrayList;

public interface IMainView {
    void updateRecyclerView(ArrayList<Group> groups);

    void startLoginActivity();

    void loadUserTypeOrganiser();

    void loadUserTypeParticipant();
}
