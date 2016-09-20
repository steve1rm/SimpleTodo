package com.codepath.simpletodo.addItems;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.simpletodo.R;

/**
 * Created by steve on 9/20/16.
 */

public class AddNewTaskDialog {

    public interface AddNewTaskDialogListener {
        void onAddNewTaskDialog(String taskName);
    }

    private AddNewTaskDialog() {}

    public static AddNewTaskDialog getNewInstance() {
        return new AddNewTaskDialog();
    }

    public void createNewTaskDialog(final Context context, final AddNewTaskDialogListener addNewTaskDialogListener) {
        final AlertDialog builder = new AlertDialog.Builder(context, R.style.NewTaskDialog)
                .setPositiveButton(R.string.add_task, null)
                .setNegativeButton(R.string.cancel_task, null)
                .create();

        final EditText etNickName = new EditText(context);
        builder.setView(etNickName);
        builder.setTitle(R.string.todo_title);
        builder.setMessage(context.getString(R.string.new_task_message));

        /* User cannot cancel by clicking outsite the dialog area */
        builder.setCancelable(false);

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnAccept = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etNickName.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter a new task name", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            addNewTaskDialogListener.onAddNewTaskDialog(etNickName.getText().toString());
                            builder.dismiss();
                        }
                    }
                });

                final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etNickName.setText("");
                        builder.dismiss();
                    }
                });
            }
        });

        /* Show the dialog */
        builder.show();
    }
}
