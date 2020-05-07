package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mDurationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDurationInput = findViewById(R.id.durationInput);

        final Workout w = new Workout("run", 8);


        Button pressUpsBtn = findViewById(R.id.startTimer);
        pressUpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Workout workout = createWorkout("Press ups");
                openTimerActivity(workout);
            }
        });

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


    private Workout createWorkout(String exercise) {
        Workout workout = new Workout();

        switch (exercise) {
            case "Press ups":
                workout.setExercise(exercise);
                workout.setDuration(parseInt(String.valueOf(mDurationInput.getText())));
                break;
        }
        return workout;
    }







    private void openTimerActivity(Workout workout) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }

}
