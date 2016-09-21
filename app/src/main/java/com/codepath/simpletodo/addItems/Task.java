package com.codepath.simpletodo.addItems;

/**
 * Created by steve on 9/20/16.
 */

public class Task {
    private String mTaskName;

    private Task() {
    }

    public static Task getNewInstance() {
        return new Task();
    }

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        this.mTaskName = taskName;
    }
}
