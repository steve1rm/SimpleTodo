package com.codepath.simpletodo.addItems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.simpletodo.R;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.R.string.no;

/**
 * Created by steve on 9/19/16.
 */

public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.TodoViewHolder> {

    public interface TaskListListener {
        void onTaskListEdit(int position);
        void onTaskListDelete(int position);
    }
    private TaskListListener mTaskListListener;

    private List<Task> tasksList = Collections.emptyList();

    public AdapterTodo(List<Task> tasksList, TaskListListener taskListListener) {
        this.tasksList = tasksList;
        mTaskListListener = taskListListener;
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
    public void onBindViewHolder(TodoViewHolder holder, final int position) {
        holder.mEtTaskName.setText(tasksList.get(position).getTaskName());
    }

    public void addNewTask(Task task) {
        tasksList.add(task);
        notifyItemInserted(tasksList.size() - 1);
    }

    public void deleteTask(int position) {
        tasksList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateTask(Task task, int position) {
        tasksList.set(position, task);
        notifyItemChanged(position);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.etTaskName) TextView mEtTaskName;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(TodoViewHolder.this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timber.d("onclick position: " + getAdapterPosition());
                    /* Open dialog for editing */
                    mTaskListListener.onTaskListEdit(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Timber.d("onLongClick: " + getAdapterPosition());
                    /* deletion of task */
                    mTaskListListener.onTaskListDelete(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
