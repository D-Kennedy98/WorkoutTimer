// Author: Dominic Kennedy 160304253

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

public class WorkoutActivity extends AppCompatActivity {
   private EditText mDurationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // retrieve duration intent
        Intent mainIntent = getIntent();
        final Workout workout = mainIntent.getParcelableExtra("workout");

        // init views
        mDurationInput = findViewById(R.id.duration_et);
        TextView exerciseTitle = findViewById(R.id.workout_title);
        TextView categoryTxt = findViewById(R.id.category_Txt);
        ImageView exerciseImg = findViewById(R.id.workout_img);
        Button startBtn = findViewById(R.id.start_btn);
        ImageView backBtn = findViewById(R.id.back_btn);

        // set views
        exerciseTitle.setText(workout.getExercise());
        categoryTxt.setText(workout.getCategory());
        exerciseImg.setImageResource(workout.getImageResource());

        // set on click listeners
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout.setDuration(getDuration());
                durationInputCheck(workout);
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

    // error check parsing from et
    private Integer parseInt(String input) {
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    // check a duration input has been entered
    private void durationInputCheck(Workout workout) {
        if (getDuration() != 0) {
            launchTimerActivity(workout);
        } else {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,"Enter a duration!", duration);
            toast.show();
        }
    }

    private void launchTimerActivity(Workout workout) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
