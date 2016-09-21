package com.codepath.simpletodo.addItems;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.codepath.simpletodo.R;
import com.codepath.simpletodo.utils.FileOperations;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddView extends Fragment implements AddNewTaskDialog.AddNewTaskDialogListener {

    @BindView(R.id.tbTodo) Toolbar mTbTodo;
    @BindView(R.id.rvTasks) RecyclerView mRvTasks;

    private static final String TODO_FILE = "todo.txt";
    private Unbinder mUnbinder;
    private List<Task> tasksList = Collections.emptyList();
    private AdapterTodo mAdapterTodo;

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

        /* Get any tasks from file */
        FileOperations.readItems(getActivity().getFilesDir(), TODO_FILE);
        setupRecyclerView();

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

    /** Setup recyclerview */
    public void setupRecyclerView() {
        mAdapterTodo = new AdapterTodo();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRvTasks.setLayoutManager(linearLayout);
        mRvTasks.setAdapter(mAdapterTodo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        FileOperations.writeItems(getActivity().getFilesDir(), TODO_FILE, tasksList);
    }

    /** Show the dialog box that will allow the user to enter a new task */
    private void showAddNewTaskDialog() {
        AddNewTaskDialog.getNewInstance().createNewTaskDialog(getActivity(), AddView.this);
    }

    @Override
    public void onAddNewTaskDialog(String taskName) {
        Toast.makeText(getActivity(), "New Task: " + taskName, Toast.LENGTH_SHORT).show();
        Task task = Task.getNewInstance();
        task.setTaskName(taskName);

        mAdapterTodo.addNewTask(task);
    }
}
