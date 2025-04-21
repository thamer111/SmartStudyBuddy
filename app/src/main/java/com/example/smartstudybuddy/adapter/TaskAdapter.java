package com.example.smartstudybuddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartstudybuddy.R;
import com.example.smartstudybuddy.data.Task;

import java.util.List;

// Simple adapter for the task list — Thamer Ali
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;

    public TaskAdapter(List<Task> initialTasks) {
        this.tasks = initialTasks;
    }

    // ViewHolder holds the item views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, dueView, courseView;
        CheckBox doneCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView      = itemView.findViewById(R.id.task_title);
            dueView        = itemView.findViewById(R.id.task_dueDate);
            courseView     = itemView.findViewById(R.id.task_course);
            doneCheckbox   = itemView.findViewById(R.id.task_status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task current = tasks.get(position);
        holder.titleView.setText(current.getTitle());
        holder.dueView.setText(current.getDueDate());
        holder.courseView.setText(current.getCourseName());
        holder.doneCheckbox.setChecked(current.isCompleted());
        // TODO: add listener if we want to toggle completion here
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // update the list of tasks and refresh
    public void setTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }
}
