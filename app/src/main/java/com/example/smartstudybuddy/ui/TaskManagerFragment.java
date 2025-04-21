package com.example.smartstudybuddy.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartstudybuddy.R;
import com.example.smartstudybuddy.adapter.TaskAdapter;
import com.example.smartstudybuddy.data.AppDatabase;
import com.example.smartstudybuddy.data.Task;
import com.example.smartstudybuddy.data.TaskDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// Task manager screen – Thamer Ali
public class TaskManagerFragment extends Fragment {

    private TaskDao taskDao;
    private TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_task_manager, container, false);

        // set up RecyclerView
        RecyclerView list = root.findViewById(R.id.recycler_view_tasks);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(new ArrayList<>());
        list.setAdapter(adapter);

        // add‑task button
        FloatingActionButton fab = root.findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> openNewTaskDialog());

        // get the DAO and start observing
        taskDao = AppDatabase.getDatabase(requireContext()).taskDao();
        taskDao.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        return root;
    }

    // show a simple dialog to add a task
    private void openNewTaskDialog() {
        View form = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_task, null);
        new AlertDialog.Builder(getContext())
                .setTitle("New Task")
                .setView(form)
                .setPositiveButton("Add", (dlg, which) -> {
                    String title   = ((EditText) form.findViewById(R.id.et_task_title))
                            .getText().toString();
                    String dueDate = ((EditText) form.findViewById(R.id.et_task_due_date))
                            .getText().toString();
                    String course  = ((EditText) form.findViewById(R.id.et_course_name))
                            .getText().toString();

                    // insert off the UI thread
                    new Thread(() -> taskDao.insertTask(
                            new Task(title, dueDate, course, false)
                    )).start();

                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
