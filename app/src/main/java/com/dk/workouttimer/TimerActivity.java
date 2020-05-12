/*
 Author: Dominic Kennedy 160304253
 Purpose: Implements Timer screen which displays the workout timer
 */

package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private Button mStartPauseBtn;
    private boolean mIsTimerRunning;
    private CountDownTimer mTimer;
    private int mDuration;
    private TextView mTimerValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // retrieve duration intent
        final Intent mainIntent = getIntent();
        Workout workout = mainIntent.getParcelableExtra("workout");
        mDuration = workout.getDuration() * 1000;

        // init views
        mTimerValueText = findViewById(R.id.timer_value);
        TextView workoutText = findViewById(R.id.workout_title);
        mStartPauseBtn = findViewById(R.id.pause_btn);
        Button restartBtn = findViewById(R.id.restart_btn);
        ImageView mBackBtn = findViewById(R.id.back_btn);

        // set views
        mStartPauseBtn.setText(R.string.pause);
        workoutText.setText(workout.getExercise());

        startCountdown();

        // set on click listeners
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mStartPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsTimerRunning) {
                    startCountdown();
                    mStartPauseBtn.setText(R.string.pause);
                } else {
                    pauseTimer();
                }
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(mainIntent);
            }
        });

    }

    // create count down timer
    private void startCountdown() {
        mTimer = new CountDownTimer(mDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDuration = (int) millisUntilFinished;
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mTimerValueText.setTextSize(72);
                mTimerValueText.setText(R.string.workout_complete);
            }
        }.start();

        mIsTimerRunning = true;
    }

    private void pauseTimer() {
        mTimer.cancel();
        mIsTimerRunning = false;
        mStartPauseBtn.setText(R.string.start);
    }

    // format time to mm:ss and set value of timer
    private void updateTimerText(long timeRemaining) {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        String formTimeRemaining = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTimerValueText.setText(formTimeRemaining);

    }

}