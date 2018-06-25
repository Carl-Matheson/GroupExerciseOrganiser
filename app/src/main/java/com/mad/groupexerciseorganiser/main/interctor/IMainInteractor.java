package com.mad.groupexerciseorganiser.main.interctor;

import com.mad.groupexerciseorganiser.models.Group;

public interface IMainInteractor {
    void userState();

    void databaseListener();

    void logout();

    void addAuthStateListener();

    Group getGroupData(int position);
}
