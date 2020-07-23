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

    /**
     * vars for logging exercises being added to arrayList
     */
    private static final String TAG = "";
    private static int logCount = 0;

    /**
     * edit text elements for inputting workout title, exercise name and duration
     */
    private EditText mWorkoutTitleInput;
    private EditText mNameInput;
    private EditText mDurationInput;


    /**
     * array list to store exercise objects that make up a workout.
     * passed as intent back to workout activity to populate recycler view
     */
    private ArrayList<Exercise> mExerciseArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);



        // btn to create WO objects using edit text values
        Button addExerciseBtn = findViewById(R.id.add_exercise_btn);

        // btn to navigate back to routines activity
        ImageView backBtn = findViewById(R.id.back_btn_cw);

        mWorkoutTitleInput = findViewById(R.id.workout_title_input);
        mNameInput = findViewById(R.id.name_input);
        mDurationInput = findViewById(R.id.duration_input);

        //mExerciseArrayList = new ArrayList<>();

        /* create new exercise object from edit text values and add to arrayList
         * TODO: Error checking input, make sure it doesn't exceed max CDT value
         */
        addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExerciseArrayList.add(new Exercise(getNameInput(), getDurationInput()));

                // clear edit texts once exercise is added
                mDurationInput.getText().clear();
                mNameInput.getText().clear();

                // display msg if exercise is successfully added
                Context context = getApplicationContext();
                int toastDuration = Toast.LENGTH_SHORT;
                Toast woAddedToast = Toast.makeText(context, "Exercise Added", toastDuration);
                woAddedToast.show();

                // log objects being added to arrayList
                logCount = mExerciseArrayList.size() - 1;
                Log.i(TAG, String.valueOf(mExerciseArrayList.size()));
                Log.i(TAG, mExerciseArrayList.get(logCount).getName() + " " + mExerciseArrayList.get(logCount).getDuration());
            }
        });

        // navigate back to routines with workout object as intent
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchWorkoutsActivity(
//                        new Workout(getWorkoutTitleInput(), calcTotalDuration(mExerciseArrayList),
//                        mExerciseArrayList.size(), mExerciseArrayList)
//                );
//
//            }
//        });

    }

    /**
     * get the workout title from the edit text for creating a workout object
     * @return the inputted workout title
     */
    private String getWorkoutTitleInput() {
        return mWorkoutTitleInput.getText().toString();
    }

    /**
     * gets the exercise name from edit text for creating a exercise object
     * @return the inputted exercise name
     */
    private String getNameInput() {
        return mNameInput.getText().toString();
    }


    /**
     * gets the duration that the exercise will last for from edit text for creating a exercise object
     * @return the inputted duration for the workout
     */
    private int getDurationInput() {
        return parseInt(String.valueOf(mDurationInput.getText()));
    }

//    /**
//     * launch timer activity and pass the exercise arrayList as intent
//     * TODO: will go to routines before timer when storage is implemented
//     * @param workoutArrayList stores the workout
//     */
//    private void launchTimerActivity(ArrayList<Workout> workoutArrayList) {
//        Intent intent = new Intent(this, TimerActivity.class);
//        intent.putExtra("workoutArrayList", workoutArrayList);
//        startActivity(intent);
//
//    }

    /**
     * launch workouts activity and pass workout object as intent
     * @param workout stores the workout data
     */
    private void launchWorkoutsActivity(Workout workout) {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }

    /**
     * calculate total duration of all exercises in the workout
     * @param exerciseArrayList stores the exercise objects which will be assigned to workout field
     * @return total duration of all exercises in workout
     */
    int calcTotalDuration(ArrayList<Exercise> exerciseArrayList) {
        int totalDuration = 0;

        for(int i = 0; i < exerciseArrayList.size(); i++) {
            totalDuration += exerciseArrayList.get(i).getDuration();
        }

        return totalDuration;
    }



}
