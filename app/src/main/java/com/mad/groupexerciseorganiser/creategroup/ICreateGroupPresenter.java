package com.mad.groupexerciseorganiser.creategroup;

import android.net.Uri;

public interface ICreateGroupPresenter {
    void validateGroup(String name, String gender, String sport, Uri uri);

    void onEmptyDetails();

    void onSuccess();

    void onError(String error);
}
