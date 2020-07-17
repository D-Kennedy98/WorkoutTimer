package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CreateWorkoutActivity extends AppCompatActivity {

    private static final String TAG = "";
    private static int logCount = 0;

    /**
     * edit text elements for inputting workout exercise and duration
     */
    private EditText mExerciseInput;
    private EditText mDurationInput;


    /**
     * array list to store WO objects that make up a routine.
     * passed as intent back to RoutinesAct to populate recycler view
     */
    private ArrayList<Workout> workoutArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        // btn to create WO objects using edit text values
        Button addWOBtn = findViewById(R.id.add_WO_btn);

        // btn to navigate back to routines activity
        ImageView backBtn = findViewById(R.id.back_btn_cw);

        mExerciseInput = findViewById(R.id.exercise_input);
        mDurationInput = findViewById(R.id.duration_input);

        workoutArrayList = new ArrayList<>();

        // create new WO object from edit text values and add to arrayList
        // TODO: Error checking input, make sure it doesn't exceed max CDT value
        addWOBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutArrayList.add(new Workout(getExerciseInput(), getDurationInput()));

                mDurationInput.getText().clear();
                mExerciseInput.getText().clear();

                // display msg if WO is successfully added
                Context context = getApplicationContext();
                int toastDuration = Toast.LENGTH_SHORT;
                Toast woAddedToast = Toast.makeText(context, "Workout Added", toastDuration);
                woAddedToast.show();

                // log objects being added to arrayList
                logCount = workoutArrayList.size() - 1;
                Log.i(TAG, String.valueOf(workoutArrayList.size()));
                Log.i(TAG, workoutArrayList.get(logCount).getExercise() + " " + workoutArrayList.get(logCount).getDuration());
            }
        });

        // navigate back to routines with WO arrayList as intent
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTimerActivity(workoutArrayList);
            }
        });

    }


    /**
     * gets the exercise name from edit text to create a WO object
     * @return the inputted exercise name
     */
    private String getExerciseInput() {
        return mExerciseInput.getText().toString();
    }


    /**
     * gets the duration that the workout will last for from edit text to create a WO object
     * @return the inputted duration for the workout
     */
    private int getDurationInput() {
        return parseInt(String.valueOf(mDurationInput.getText()));
    }

    /**
     * launch timer activity and pass the WO arrayList as intent
     * TODO: will go to routines before timer when storage is implemented
     * @param workoutArrayList stores the workout objects which will be passed to CDT
     */
    private void launchTimerActivity(ArrayList<Workout> workoutArrayList) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workoutArrayList", workoutArrayList);
        startActivity(intent);

    }



}
