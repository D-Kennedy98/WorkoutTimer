package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {
    TextView mExerciseTitle;
    ImageView mExerciseImg;
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
        mDurationInput = findViewById(R.id.durationInput);
        mStartBtn = findViewById(R.id.StartBtn);
        mExerciseImg = findViewById(R.id.exerciseImg);

        mExerciseTitle.setText(workout.getExercise());
        updateDescription(workout);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout.setDuration(getDuration());
                launchTimerActivity(workout);
            }
        });

    }


    private void updateDescription(Workout w) {
        switch (w.getExercise()) {
            case "Press Ups":
                mExerciseImg.setImageResource(R.drawable.press_upp);
                break;
            case "Plank":
                mExerciseImg.setImageResource(R.drawable.plankk);
                break;
            case "High Knees":
                mExerciseImg.setImageResource(R.drawable.high_knees);
                break;
            case "Yoga":
                mExerciseImg.setImageResource(R.drawable.yoga);
                break;
            case "Burpees":
                mExerciseImg.setImageResource(R.drawable.burpeess);
                break;
            case "Squats":
                // TODO: Image with more white bg
                mExerciseImg.setImageResource(R.drawable.squat_example);
                break;
        }

    }

    // get duration from user input
    // TODO: Check if value has been inputted
    private int getDuration() {
        return parseInt(String.valueOf(mDurationInput.getText()));
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
