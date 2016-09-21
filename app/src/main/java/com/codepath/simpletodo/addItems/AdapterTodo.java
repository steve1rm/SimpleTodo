package com.codepath.simpletodo.addItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepath.simpletodo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve on 9/19/16.
 */

public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.TodoViewHolder> {

    private List<Task> tasksList = Collections.emptyList();

    public AdapterTodo() {
        this.tasksList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.mEtTaskName.setText(tasksList.get(position).getTaskName());
    }

    public void addNewTask(Task task) {
        tasksList.add(task);
        notifyItemInserted(tasksList.size() - 1);
    }

    public void deleteTask(Task task) {
        final int position = tasksList.indexOf(task);
        tasksList.remove(task);
        notifyItemRemoved(position);
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etTaskName) EditText mEtTaskName;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(TodoViewHolder.this, itemView);
        }
    }
}
