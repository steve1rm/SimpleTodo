package com.codepath.simpletodo.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by steve on 9/28/16.
 */

// @RealmClass
public class TodoItems extends RealmObject {
    @PrimaryKey
    private String mId;
    private String mTaskName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        this.mTaskName = taskName;
    }
}
