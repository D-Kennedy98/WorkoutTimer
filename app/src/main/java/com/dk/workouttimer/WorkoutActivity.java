package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {
    TextView mExerciseTitle, mDescription;
    EditText mDurationInput;
    Button mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // retrieve duration intent
        Intent mainIntent = getIntent();
        final Workout workout = mainIntent.getParcelableExtra("workout");


        mExerciseTitle = findViewById(R.id.exerciseTitle);
        mDescription = findViewById(R.id.description);
        mDurationInput = findViewById(R.id.durationInput);
        mStartBtn = findViewById(R.id.startBtn);

        mExerciseTitle.setText(workout.getExercise());
        updateDescription(workout);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = getDuration();
                workout.setDuration(duration);
                launchTimerActivity(workout);
            }
        });

    }


    private void updateDescription(Workout w) {
        switch (w.getExercise()) {
            case "Press Ups":
                mDescription.setText(getResources().getString(R.string.descrPH));
            break;
            case "2":
                mDescription.setText(getResources().getString(R.string.descrPH));


        }

    }

    // get duration from user input
    // TODO: Check if value has been inputted
    private int getDuration() {
        int duration = parseInt(String.valueOf(mDurationInput.getText()));
        return duration;
    }


    // error checks parsing from editText
    private Integer parseInt(Object obj) {
        int value;
        try {
            value = Integer.parseInt((String) obj);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    private void launchTimerActivity(Workout w) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", w);
        startActivity(intent);
    }



}
