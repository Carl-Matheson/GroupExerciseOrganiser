package com.mad.groupexerciseorganiser.main;

import com.mad.groupexerciseorganiser.models.Group;

public interface IMainPresenter {
    void checkUserState();

    void callDatabaseListener();

    void logout();

    void addAuthStateListener();

    Group handleGroupClick(int position);
}
