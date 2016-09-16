package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final int EDIT_RESPONSE_CODE = 1;
    public static final String UPDATE_TASK_TEXT = "update_task_text";
    public static final String LIST_INDEX = "list_index";

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

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra(UPDATE_TASK_TEXT, items.get(position));
                intent.putExtra(LIST_INDEX, position);
                startActivityForResult(intent, EDIT_RESPONSE_CODE);
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

    /** write items to the App's local file system from items contained in the list */
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == EDIT_RESPONSE_CODE) {
                /* Update the task in the list view and write update to file */
                boolean isSuccess = true;

                String updatedText = "";
                int listIndex = -1;

                if(data != null) {
                    if(data.hasExtra(UPDATE_TASK_TEXT)) {
                        updatedText = data.getStringExtra(UPDATE_TASK_TEXT);
                    }
                    else {
                        isSuccess = false;
                    }

                  if(data.hasExtra(LIST_INDEX)) {
                        listIndex = data.getIntExtra(LIST_INDEX, -1);
                    }
                    else {
                        isSuccess = false;
                    }

              }
                else {
                    isSuccess = false;
                }

                if(isSuccess) {
                    items.set(listIndex, updatedText);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed to update tast", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Invalid Edit", Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Edit Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
