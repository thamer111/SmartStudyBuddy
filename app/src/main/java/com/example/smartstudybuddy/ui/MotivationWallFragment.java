package com.example.smartstudybuddy.ui;

import android.app.AlertDialog;
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
import com.example.smartstudybuddy.data.Quote;

import java.util.List;
import java.util.Random;

// Motivation wall – Thamer Ali
public class MotivationWallFragment extends Fragment {

    private TextView quoteDisplay;
    private Button addQuoteBtn;
    private AppDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_motivation_wall, parent, false);

        quoteDisplay = root.findViewById(R.id.tv_quote);
        addQuoteBtn  = root.findViewById(R.id.btn_add_quote);
        database     = AppDatabase.getDatabase(requireContext());

        addQuoteBtn.setOnClickListener(v -> openQuoteDialog());
        showRandomQuote();

        return root;
    }

    // fetch quotes in background and show one at random
    private void showRandomQuote() {
        new Thread(() -> {
            List<Quote> list = database.quoteDao().getAllQuotes().getValue();

            // Make this final so lambdas can capture it
            final String displayText;
            if (list != null && !list.isEmpty()) {
                int idx = new Random().nextInt(list.size());
                displayText = list.get(idx).getText();
            } else {
                displayText = "Stay focused and keep learning!";
            }

            requireActivity().runOnUiThread(() ->
                    quoteDisplay.setText(displayText)
            );
        }).start();
    }

    // display a simple dialog to add a new motivational quote
    private void openQuoteDialog() {
        View form = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_quote, null);
        new AlertDialog.Builder(getContext())
                .setTitle("New Quote")
                .setView(form)
                .setPositiveButton("Save", (dlg, which) -> {
                    String input = ((EditText) form.findViewById(R.id.et_quote))
                            .getText().toString();
                    new Thread(() -> {
                        database.quoteDao().insertQuote(new Quote(input));
                        showRandomQuote(); // update display
                    }).start();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
