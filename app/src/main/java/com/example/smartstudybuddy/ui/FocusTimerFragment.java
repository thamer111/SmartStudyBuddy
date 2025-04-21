package com.example.smartstudybuddy.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.smartstudybuddy.R;

// Pomodoro-style timer screen — by Thamer Ali
public class FocusTimerFragment extends Fragment {

    private CountDownTimer countdown;
    private boolean running = false;
    private long timeLeft = 25 * 60 * 1000; // 25 min in ms

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_focus_timer, parent, false);

        TextView timerDisplay = root.findViewById(R.id.tv_timer);
        Button startPauseBtn  = root.findViewById(R.id.btn_start_pause);
        Button resetBtn       = root.findViewById(R.id.btn_reset);

        // Start or pause on click
        startPauseBtn.setOnClickListener(v -> {
            if (running) {
                stopTimer(timerDisplay);
            } else {
                beginTimer(timerDisplay);
            }
        });

        // Reset back to full session
        resetBtn.setOnClickListener(v -> {
            timeLeft = 25 * 60 * 1000;
            updateDisplay(timerDisplay);
            running = false;
        });

        // Initialize display
        updateDisplay(timerDisplay);
        return root;
    }

    private void beginTimer(TextView display) {
        countdown = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long msUntilFinished) {
                timeLeft = msUntilFinished;
                updateDisplay(display);
            }
            @Override
            public void onFinish() {
                running = false;
                // TODO: could trigger a break period here
            }
        }.start();
        running = true;
    }

    private void stopTimer(TextView display) {
        if (countdown != null) {
            countdown.cancel();
        }
        running = false;
    }

    // Update the TextView with minutes:seconds
    private void updateDisplay(TextView display) {
        int mins = (int) (timeLeft / 1000) / 60;
        int secs = (int) (timeLeft / 1000) % 60;
        String timeText = String.format("%02d:%02d", mins, secs);
        display.setText(timeText);
    }
}
