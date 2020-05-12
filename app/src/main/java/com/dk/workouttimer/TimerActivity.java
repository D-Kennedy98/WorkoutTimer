package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView mTimerValueText;
    private int mDuration;
    private Button mStartPauseBtn;
    private boolean mIsTimerRunning;
    private CountDownTimer mTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // retrieve duration intent
        final Intent mainIntent = getIntent();
        Workout workout = mainIntent.getParcelableExtra("workout");

        mTimerValueText = findViewById(R.id.timerValue);
        TextView workoutText = findViewById(R.id.exercise);
        mStartPauseBtn = findViewById(R.id.pauseBtn);
        mStartPauseBtn.setText("Pause");
        Button restartBtn = findViewById(R.id.restartBtn);
        ImageView mBackBtn = findViewById(R.id.backBtn);

        workoutText.setText(workout.getExercise());
        mDuration = workout.getDuration() * 1000;

        startCountdown();

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
                    mStartPauseBtn.setText("Pause");
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

    private void startCountdown() {
        mTimer = new CountDownTimer(mDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDuration = (int) millisUntilFinished;
               // mTimerValueText.setText(String.valueOf(millisUntilFinished / 1000));
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mTimerValueText.setTextSize(72);
                mTimerValueText.setText("Workout Complete!");
            }
        }.start();

        mIsTimerRunning = true;
    }


    private void pauseTimer() {
        mTimer.cancel();
        mIsTimerRunning = false;
        mStartPauseBtn.setText("Start");
    }


    private void updateTimerText(long timeRemaining) {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        String formTimeRemaining = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTimerValueText.setText(formTimeRemaining);

    }


}
