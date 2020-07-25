package com.dk.workouttimer.activities;

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

import com.dk.workouttimer.App;
import com.dk.workouttimer.R;
import com.dk.workouttimer.models.Exercise;
import com.dk.workouttimer.models.Workout;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CreateWorkoutActivity extends AppCompatActivity {

    /**
     * vars for logging exercises being added to arrayList
     */
    private static final String TAG = "";
    private static int logCount = 0;


    /**
     * store exercise name to be passed into exercise constructor
     */
    private String mExerciseName;


    /**
     * store workout title to be passed into workout constructor
     */
    private String mWorkoutTitle;


    // TODO: appropriate max lengths
    /**
     * store max length that of exercise name
     */
    private static final int MAX_WORKOUT_TITLE_LENGTH = 35;

    /**
     * store max length of workout title
     */
    private static final int MAX_EXERCISE_NAME_LENGTH = 30;


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

        final App app = (App)getApplication();

        // btn to create WO objects using edit text values
        Button addExerciseBtn = findViewById(R.id.add_exercise_btn);

        // btn to navigate back to routines activity
        ImageView backBtn = findViewById(R.id.back_btn_cw);

        // btn to save workout and navigate back to workouts activity
        Button saveBtn = findViewById(R.id.save_btn);

        mWorkoutTitleInput = findViewById(R.id.workout_title_input);
        mNameInput = findViewById(R.id.name_input);
        mDurationInput = findViewById(R.id.duration_input);


        /*
         * create new exercise object from edit text values and add to arrayList
         * TODO: Error checking input, make sure it doesn't exceed max CDT value
         */
        addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNameInput()) {
                    mExerciseArrayList.add(new Exercise(mExerciseName, getDurationInput()));
                    mDurationInput.getText().clear();
                    mNameInput.getText().clear();
                }
            }
        });


        /*
         *  create workout object and add to database
         *  then launch workouts activity
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWorkoutTitleInput()) {
                    Workout newWorkout = new Workout(
                            mWorkoutTitle, calcTotalDuration(mExerciseArrayList),
                            mExerciseArrayList.size(), mExerciseArrayList);

                    app.workoutDao.clearTable(); // Clear table
                    app.workoutDao.insertWorkout(newWorkout);

                    launchWorkoutsActivity();
                }
            }
        });

    }


    /**
     * get the workout title from the edit text for creating a workout object
     * @return the inputted workout title
     */
    private Boolean getWorkoutTitleInput() {
        String title = mWorkoutTitleInput.getText().toString();
        Context contextWO = getApplicationContext();

        if (title.equals("")) {
            Toast titleToast = Toast.makeText(contextWO, "Enter a workout title! (Max 35 characters)", Toast.LENGTH_SHORT);
            titleToast.show();
            return false;
        } else if (title.length() > MAX_WORKOUT_TITLE_LENGTH) {
            Toast nameToast = Toast.makeText(contextWO, "Workout title must not exceed 35 characters", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else {
            mWorkoutTitle = title;
            return true;
        }
    }


    /**
     * get the exercise name from edit text and
     * check its a valid string (not empty or exceeding 35 chars)
     * @return true if string checks pass / false if string checks fail
     */
    private Boolean getNameInput() {
        String name = mNameInput.getText().toString();
        Context contextEx = getApplicationContext();

        if (name.equals("")) {
            Toast nameToast = Toast.makeText(contextEx, "Enter an exercise name! (Max 30 characters)", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else if (name.length() > MAX_EXERCISE_NAME_LENGTH) {
            Toast nameToast = Toast.makeText(contextEx, "Exercise name must not exceed 30 characters", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else {
            mExerciseName = name;
            return true;
        }
    }


    /**
     * get the duration that the exercise will last for from edit text for creating a exercise object
     * @return the inputted duration for the workout
     */
    private int getDurationInput() {
        if (mDurationInput.getText() == null) {
            Log.i("Duration", "duration");
            Context context = getApplicationContext();
            Toast nameToast = Toast.makeText(context, "Enter a duration", Toast.LENGTH_SHORT);
            nameToast.show();
        }

        return parseInt(String.valueOf(mDurationInput.getText()));
    }


    /**
     * launch workouts activity after workout has been saved
     */
    private void launchWorkoutsActivity() {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        startActivity(intent);
    }


    /**
     * calculate total duration of all exercises in the workout
     * @param exerciseArrayList stores the exercise objects which will be assigned to workout field
     * @return total duration of all exercises in workout
     */
    private int calcTotalDuration(ArrayList<Exercise> exerciseArrayList) {
        int totalDuration = 0;

        for(int i = 0; i < exerciseArrayList.size(); i++) {
            totalDuration += exerciseArrayList.get(i).getDuration();
        }

        return totalDuration;
    }

}
