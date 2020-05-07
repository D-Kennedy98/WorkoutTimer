package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    private TextView mTimerValueText, mWorkoutText;
    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // retrieve duration intent
        Intent mainIntent = getIntent();
        workout = mainIntent.getParcelableExtra("workout");

        mTimerValueText = findViewById(R.id.timerValue);
        mWorkoutText = findViewById(R.id.exercise);

        mWorkoutText.setText(workout.getExercise());
        startCountdown(workout.getDuration());

    }

    // create count down timer
    private void startCountdown(int duration) {
        new CountDownTimer((duration * 1000), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerValueText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}
