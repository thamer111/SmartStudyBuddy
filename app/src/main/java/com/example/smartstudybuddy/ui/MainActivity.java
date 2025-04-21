package com.example.smartstudybuddy.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.smartstudybuddy.R;
import com.example.smartstudybuddy.ui.FocusTimerFragment;
import com.example.smartstudybuddy.ui.GPACalculatorFragment;
import com.example.smartstudybuddy.ui.MotivationWallFragment;
import com.example.smartstudybuddy.ui.TaskManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout from res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        // Set header text
        TextView tvStudentName = findViewById(R.id.tv_student_name);
        TextView tvStudentCrn = findViewById(R.id.tv_student_crn);
        tvStudentName.setText("Student Name: Thamer Alyamani");
        tvStudentCrn.setText("CRN: 22598");

        // Setup BottomNavigationView and its menu handling using if/else
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_tasks) {
                loadFragment(new TaskManagerFragment());
                return true;
            } else if (id == R.id.nav_timer) {
                loadFragment(new FocusTimerFragment());
                return true;
            } else if (id == R.id.nav_quotes) {
                loadFragment(new MotivationWallFragment());
                return true;
            } else if (id == R.id.nav_gpa) {
                loadFragment(new GPACalculatorFragment());
                return true;
            }
            return false;
        });

        // Load the default fragment (e.g., TaskManagerFragment) when the app starts
        loadFragment(new TaskManagerFragment());
    }

    // Helper method to replace the fragment in the fragment container
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
