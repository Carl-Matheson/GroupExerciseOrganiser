package com.mad.groupexerciseorganiser.creategroup.interactor;

import android.net.Uri;

/**
 * Basic interface for the interactor
 */

public interface ICreateGroupInteractor {
    void validateGroup(OnCreateGroupFinishedListener listener, String name, String gender, String sport, Uri uri);
}
