package com.codepath.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);

        /* Populate the list items if they are contained on file */
        readItems();

        itemsAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                items);

        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    /** Add an item to the list */
    public void onAddItem(View view) {
        final EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        final String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.getText().clear();

        writeItems();
    }

    /** Remove an item from the list */
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();

                writeItems();
                return true;
            }
        });
    }

    /** Read items from file and populate the list */
    private void readItems() {
        final File fileDir = getFilesDir();
        final File todoFile = new File(fileDir, "todo.txt");

        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        }
        catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    /** write items to the file system from items contained in the list */
    private void writeItems() {
        final File fileDir = getFilesDir();
        final File todoFile = new File(fileDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
