package com.codepath.simpletodo.utils;

import com.codepath.simpletodo.addItems.Task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by steve on 9/20/16.
 */

public final class FileOperations {

    /**
     * Read items from file and populate the list
     */
    public static List<Task> readItems(File fileDir, String filename) {
        final File todoFile = new File(fileDir, filename);
        List<String> items;
        List<Task> tasksList = new ArrayList<>();

        try {
            FileUtils.readLines(todoFile);

            items = new ArrayList<>(FileUtils.readLines(todoFile));

            for(String taskName : items) {
                Task tasks = Task.getNewInstance();
                tasks.setTaskName(taskName);
                tasksList.add(tasks);
            }
        }
        catch (IOException e) {
            Timber.e("Failed to read from file %s", e.getMessage());
        }

        return tasksList;
    }

    /**
     * write items to the App's local file system from items contained in the list
     */
    public static void writeItems(File fileDir, String filename, List<Task> taskItems) {
        final File todoFile = new File(fileDir, filename);

        final List<String> items = new ArrayList<>();

        try {
            for(Task tasks: taskItems) {
                items.add(tasks.getTaskName());
            }

            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
