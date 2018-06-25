package com.mad.groupexerciseorganiser.creategroup;

public interface ICreateGroupView {
    void createNewGroup();

    void groupSuccessfullyCreated();

    void emptyDetails();

    void error(String error);

    void imagePicker();
}
