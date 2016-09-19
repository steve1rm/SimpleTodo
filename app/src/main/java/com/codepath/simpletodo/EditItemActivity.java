package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.simpletodo.addItems.MainActivity;

public class EditItemActivity extends Activity {
    private EditText mEtUpdateTask;
    private int mListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mEtUpdateTask = (EditText)findViewById(R.id.etEditTask);

        /* Get intent data */
        Intent intent = getIntent();
        String taskText = "";

        boolean isSuccess = true;

        if(intent != null) {
            if(intent.hasExtra(MainActivity.UPDATE_TASK_TEXT)) {
                taskText = intent.getStringExtra(MainActivity.UPDATE_TASK_TEXT);
            }
            else {
                isSuccess = false;
            }

            if(intent.hasExtra(MainActivity.LIST_INDEX)) {
                mListIndex = intent.getIntExtra(MainActivity.LIST_INDEX, -1);
            }
            else {
                isSuccess = false;
            }
        }
        else {
            isSuccess = false;
        }


        if(isSuccess) {
            mEtUpdateTask.setText(taskText);
        }
        else {
            Toast.makeText(EditItemActivity.this, "Failed to edit tast", Toast.LENGTH_SHORT).show();
        }
    }

    public void submitUpdate(View view) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.UPDATE_TASK_TEXT, mEtUpdateTask.getText().toString());
        intent.putExtra(MainActivity.LIST_INDEX, mListIndex);
        setResult(RESULT_OK, intent);
        finish();
    }
}
