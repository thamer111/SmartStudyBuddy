package com.example.smartstudybuddy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.smartstudybuddy.R;
import com.example.smartstudybuddy.data.AppDatabase;
import com.example.smartstudybuddy.data.GPAEntry;

import java.util.List;

// GPA calculator screen — Thamer Ali
public class GPACalculatorFragment extends Fragment {

    private EditText courseInput, creditsInput, gradeInput;
    private Button addBtn;
    private TextView gpaDisplay;
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gpa_calculator, parent, false);

        // find views
        courseInput  = root.findViewById(R.id.et_course_name);
        creditsInput = root.findViewById(R.id.et_credit_hours);
        gradeInput   = root.findViewById(R.id.et_grade);
        addBtn       = root.findViewById(R.id.btn_add_entry);
        gpaDisplay   = root.findViewById(R.id.tv_gpa);

        database = AppDatabase.getDatabase(requireContext());

        addBtn.setOnClickListener(v -> saveEntry());
        updateGPA();

        return root;
    }

    // read inputs and save a new GPA entry
    private void saveEntry() {
        String course = courseInput.getText().toString().trim();
        int credits;
        double grade;

        try { credits = Integer.parseInt(creditsInput.getText().toString()); }
        catch (Exception e) { credits = 0; }

        try { grade = Double.parseDouble(gradeInput.getText().toString()); }
        catch (Exception e) { grade = 0.0; }

        GPAEntry entry = new GPAEntry(course, credits, grade);
        new Thread(() -> {
            database.gpaEntryDao().insertEntry(entry);
            updateGPA();
        }).start();
    }

    // recalc GPA and update UI
    private void updateGPA() {
        new Thread(() -> {
            List<GPAEntry> list = database.gpaEntryDao().getAllEntries().getValue();
            int totalCred = 0;
            double qualitySum = 0;
            if (list != null) {
                for (GPAEntry e : list) {
                    qualitySum += e.getGrade() * e.getCreditHours();
                    totalCred  += e.getCreditHours();
                }
            }
            double gpa = (totalCred > 0) ? (qualitySum / totalCred) : 0.0;
            requireActivity().runOnUiThread(() ->
                    gpaDisplay.setText(String.format("Current GPA: %.2f", gpa))
            );
        }).start();
    }
}
