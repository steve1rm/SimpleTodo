package com.codepath.simpletodo.addItems;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.simpletodo.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final int EDIT_RESPONSE_CODE = 1;
    public static final String UPDATE_TASK_TEXT = "update_task_text";
    public static final String LIST_INDEX = "list_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, AddView.getNewInstance(), "AddView");
            fragmentTransaction.commit();
        }

    }


    /** Read items from file and populate the list */
    private void readItems() {
        final File fileDir = getFilesDir();
        final File todoFile = new File(fileDir, "todo.txt");

   //     try {
  //          items = new ArrayList<>(FileUtils.readLines(todoFile));
 //       }
 //       catch (IOException e) {
  //          items = new ArrayList<>();
 //       }
    }

    /** write items to the App's local file system from items contained in the list */
    private void writeItems() {
        final File fileDir = getFilesDir();
        final File todoFile = new File(fileDir, "todo.txt");
/*
        try {
  //          FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
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
 //                   items.set(listIndex, updatedText);
                    //itemsAdapter.notifyDataSetChanged();
//                    writeItems();
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
