package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutActivity extends AppCompatActivity {
    TextView mExerciseTitle, mCategoryTxt;
    ImageView mExerciseImg, mBackBtn;
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
        mCategoryTxt = findViewById(R.id.categoryTxt);
        mDurationInput = findViewById(R.id.durationInput);
        mExerciseImg = findViewById(R.id.exerciseImg);
        mStartBtn = findViewById(R.id.StartBtn);
        mBackBtn = findViewById(R.id.backBtn);


        mExerciseTitle.setText(workout.getExercise());
        mCategoryTxt.setText(workout.getCategory());
        mExerciseImg.setImageResource(workout.getmImageResource());
        //updateDescription(workout);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workout.setDuration(getDuration());
                durationInputCheck(workout);
                //launchTimerActivity(workout);
            }
        });



        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //finish();
                launchMainActivity();

            }
        });

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

    private void durationInputCheck(Workout w) {
        if (getDuration() != 0) {
            launchTimerActivity(w);
        } else {
            Context context = getApplicationContext();
            int dur = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context,"Enter a duration!", dur);
            toast.show();
        }
    }


    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
