package com.codepath.simpletodo.addItems;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItems;
import com.codepath.simpletodo.utils.FileOperations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddView extends Fragment implements
        AddNewTaskDialog.AddNewTaskDialogListener,
        EditTaskDialog.EditTaskDialogListener,
        AdapterTodo.TaskListListener {

    @BindView(R.id.tbTodo) Toolbar mTbTodo;
    @BindView(R.id.rvTasks) RecyclerView mRvTasks;

    private static final String TODO_FILE = "todo.txt";
    private Unbinder mUnbinder;
    private List<Task> tasksList = Collections.emptyList();
    private AdapterTodo mAdapterTodo;
    private Realm mRealm;

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

        setupRecyclerView();

        return view;
    }

    @OnClick(R.id.fabAddItem)
    public void addNewTask() {
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
        /* Get any tasks from file */
        tasksList = FileOperations.readItems(getActivity().getFilesDir(), TODO_FILE);

        mAdapterTodo = new AdapterTodo(tasksList, AddView.this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRvTasks.setLayoutManager(linearLayout);
        mRvTasks.setAdapter(mAdapterTodo);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        /* Create a realm TodoItems task */
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoItems todoItems = mRealm.createObject(TodoItems.class, UUID.randomUUID().toString());
                todoItems.setTaskName("cut the lawn");
            }
        });

        RealmResults<TodoItems> todoItemses = mRealm.where(TodoItems.class).findAll();
        for(TodoItems todoItem : todoItemses) {
            Timber.d("TodoItem TaskName: %s", todoItem.getTaskName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
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
        Task task = Task.getNewInstance();
        task.setTaskName(taskName);

        mAdapterTodo.addNewTask(task);
        FileOperations.writeItems(getActivity().getFilesDir(), TODO_FILE, tasksList);
    }

    @Override
    public void onTaskListDelete(int position) {
        Timber.d("onTaskListDelete: " + position);
        mAdapterTodo.deleteTask(position);
        FileOperations.writeItems(getActivity().getFilesDir(), TODO_FILE, tasksList);
    }

    @Override
    public void onTaskListEdit(int position) {
        Timber.d("onTaskListEdit: " + position);
        EditTaskDialog.getNewInstance().createEditTaskDialog(getActivity(), AddView.this, tasksList.get(position).getTaskName(), position);
    }

    @Override
    public void onEditTaskDialog(String taskName, int position) {
        Timber.d("onEditTaskDialog: " + taskName + " position: " + position);
        Task task = Task.getNewInstance();
        task.setTaskName(taskName);
        mAdapterTodo.updateTask(task, position);
        FileOperations.writeItems(getActivity().getFilesDir(), TODO_FILE, tasksList);
    }
}
