package com.codepath.simpletodo.addItems;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.codepath.simpletodo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddView extends Fragment implements AddNewTaskDialog.AddNewTaskDialogListener {

    @BindView(R.id.tbTodo) Toolbar mTbTodo;
    private Unbinder mUnbinder;

    public AddView() {
        // Required empty public constructor
    }

    public static AddView getNewInstance() {
        return new AddView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.add_view, container, false);

        mUnbinder = ButterKnife.bind(AddView.this, view);

        setupTodoToolbar();

        return view;
    }

    @OnClick(R.id.fabAddItem)
    public void addNewTask() {
        Toast.makeText(getActivity(), "Add new Item", Toast.LENGTH_SHORT).show();
        showAddNewTaskDialog();
    }

    /** Setup tool bar */
    private void setupTodoToolbar() {
        final AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mTbTodo);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /** Show the dialog box that will allow the user to enter a new task */
    private void showAddNewTaskDialog() {
        AddNewTaskDialog.getNewInstance().createNewTaskDialog(getActivity(), AddView.this);
    }

    @Override
    public void onAddNewTaskDialog(String taskName) {
        Toast.makeText(getActivity(), "New Task: " + taskName, Toast.LENGTH_SHORT).show();
    }
}
