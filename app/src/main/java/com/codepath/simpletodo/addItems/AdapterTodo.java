package com.codepath.simpletodo.addItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepath.simpletodo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve on 9/19/16.
 */

public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.TodoViewHolder> {

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.mEtTaskName.setText("placeholder");
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etTaskName) EditText mEtTaskName;

        public TodoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);
        }
    }
}
