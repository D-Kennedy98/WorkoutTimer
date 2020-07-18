/*
Author: Dominic Kennedy 160304253
Purpose: Implements workout screen where users enters the duration they wish to perform the workout for
 */

package com.dk.workouttimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WActivity extends AppCompatActivity {
   private EditText mDurationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // retrieve intent from main
        Intent mainIntent = getIntent();
        final Exercise exercise = mainIntent.getParcelableExtra("workout");

        // init views
        mDurationInput = findViewById(R.id.duration_et);
        TextView exerciseTitle = findViewById(R.id.workout_title);
        TextView categoryTxt = findViewById(R.id.category_Txt);
        ImageView exerciseImg = findViewById(R.id.workout_img);
        Button startBtn = findViewById(R.id.start_btn);
        ImageView backBtn = findViewById(R.id.back_btn);

        // set views
        exerciseTitle.setText(exercise.getName());
        //categoryTxt.setText(workout.getCategory());

        // set on click listeners
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercise.setDuration(getDuration());
                durationInputCheck(exercise);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();

            }
        });
    }

    // get duration from user input
    private int getDuration() {
        return parseInt(String.valueOf(mDurationInput.getText()));
    }

    // error check parsing from editText
    private Integer parseInt(String input) {
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    // check duration input is valid and within appropriate range
    private void durationInputCheck(Exercise exercise) {
        if (getDuration() != 0 && getDuration() <= 6000) {
            launchTimerActivity(exercise);
        } else if (getDuration() == 0) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,R.string.enter_valid_duration, duration);
            toast.show();
        } else if (getDuration() > 6000) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,R.string.value_too_high, duration);
            toast.show();
        }
    }

    private void launchTimerActivity(Exercise exercise) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", exercise);
        startActivity(intent);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        startActivity(intent);
    }

}
