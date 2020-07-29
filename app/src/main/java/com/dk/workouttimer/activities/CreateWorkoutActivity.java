/*
 * Author: Dominic Kennedy
 * Purpose: Allow user to create exercises which are used
 *          to create workout objects
 */

package com.dk.workouttimer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dk.workouttimer.App;
import com.dk.workouttimer.R;
import com.dk.workouttimer.fragment.DurationDialogFragment;
import com.dk.workouttimer.models.Exercise;
import com.dk.workouttimer.models.Workout;

import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity {

    /**
     * store exercise name to be passed into exercise constructor
     */
    private String mExerciseName;

    /**
     * store workout title to be passed into workout constructor
     */
    private String mWorkoutTitle;

    private long mExerciseDuration;

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
     * edit text elements for inputting workout title and exercise name
     */
    private EditText mWorkoutTitleInput;
    private EditText mNameInput;

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

        // btn to open dialogueFragment for entering exercise duration
        Button mDurationBtn = findViewById(R.id.duration_btn);

        // btn to navigate back to routines activity
        ImageView backBtn = findViewById(R.id.back_btn_cw);

        // btn to save workout and navigate back to workouts activity
        Button saveBtn = findViewById(R.id.save_btn);

        mWorkoutTitleInput = findViewById(R.id.workout_title_input);
        mNameInput = findViewById(R.id.name_input);

        /*
         * Open duration dialogFragment for inputting exercise duration.
         */
        mDurationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationFragment();

            }
        });

        /*
         * Create new exercise object from edit text values and add to arrayList
         * if name and duration checks pass.
         * TODO: Error checking input, make sure it doesn't exceed max CDT value
         */
        addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidNameInput() && isDurationInputted(mExerciseDuration)) {
                    mExerciseArrayList.add(new Exercise(mExerciseName, mExerciseDuration));
                    Context context = getApplicationContext();
                    Toast addToast = Toast.makeText(context, "Exercise added", Toast.LENGTH_SHORT);
                    addToast.show();

                    mNameInput.getText().clear();
                    mExerciseDuration = 0;
                }
            }
        });

        /*
         * Create workout object and add to database then launch workouts activity
         * if title input and exercise ArrayList checks pass.
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidWorkoutTitleInput() && isValidExerciseArray()) {
                    Workout newWorkout = new Workout(
                            mWorkoutTitle, (int) calcTotalDuration(mExerciseArrayList),
                            mExerciseArrayList.size(), mExerciseArrayList);

                    app.workoutDao.insertWorkout(newWorkout);
                    launchWorkoutsActivity();
                }
            }
        });

        /*
         * Go back to workouts activity without saving.
         * TODO: Are you sure message (not saved) message?
         */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWorkoutsActivity();
            }
        });


    }

//    public void initViews() {
//
//    }

    /**
     * Get the workout title from the edit text for creating a workout object.
     * //TODO: Refactor to 2 methods
     *
     * @return the inputted workout title
     */
    private Boolean isValidWorkoutTitleInput() {
        String title = mWorkoutTitleInput.getText().toString();

        if (title.equals("")) {
            Context contextWO = getApplicationContext();
            Toast titleToast = Toast.makeText(contextWO, "Enter a workout title! (Max 35 characters)", Toast.LENGTH_SHORT);
            titleToast.show();
            return false;
        } else if (title.length() > MAX_WORKOUT_TITLE_LENGTH) {
            Context contextWO = getApplicationContext();
            Toast nameToast = Toast.makeText(contextWO, "Workout title must not exceed 35 characters", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else {
            mWorkoutTitle = title;
            return true;
        }
    }

    /**
     * Check exercise array list is not empty.
     *
     * @return true if not empty | false if empty
     */
    private Boolean isValidExerciseArray() {
        if (mExerciseArrayList.size() != 0) {
            return true;
        } else {
            Context contextExArray = getApplicationContext();
            Toast ExArrayToast = Toast.makeText(contextExArray, "Workout must contain at least 1 exercise!", Toast.LENGTH_SHORT);
            ExArrayToast.show();
            return false;
        }

    }


    /**
     * Get the exercise name from edit text and
     * check its a valid string (not empty or exceeding 35 chars).
     * // TODO: Refactor into two methods
     *
     * @return true if string checks pass | false if string checks fail
     */
    private Boolean isValidNameInput() {
        String name = mNameInput.getText().toString();

        if (name.equals("")) {
            Context contextEx = getApplicationContext();
            Toast nameToast = Toast.makeText(contextEx, "Enter an exercise name! (Max 30 characters)", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else if (name.length() > MAX_EXERCISE_NAME_LENGTH) {
            Context contextEx = getApplicationContext();
            Toast nameToast = Toast.makeText(contextEx, "Exercise name must not exceed 30 characters", Toast.LENGTH_SHORT);
            nameToast.show();
            return false;
        } else {
            mExerciseName = name;
            return true;
        }
    }

    /**
     * Launch workouts activity after workout has been saved.
     */
    private void launchWorkoutsActivity() {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        startActivity(intent);
    }

    /**
     * Calculate total duration of all exercises in the workout.
     *
     * @param exerciseArrayList stores the exercise objects which will be assigned to workout field
     * @return total duration of all exercises in workout
     */
    private long calcTotalDuration(ArrayList<Exercise> exerciseArrayList) {
        long totalDuration = 0;

        for(int i = 0; i < exerciseArrayList.size(); i++) {
            totalDuration += exerciseArrayList.get(i).getDuration();
        }

        return totalDuration;
    }

    /**
     * Show dialogFragment to allow exercise duration to be inputted.
     * Update mExerciseDuration with inputted value.
     * Implements DurationListener to retrieve duration from dialogFragment.
     *
     * TODO: Look at depreciated tags
     */
    private void showDurationFragment() {
        DurationDialogFragment durationDialogFragment = new DurationDialogFragment();
        durationDialogFragment.show(getFragmentManager(), "exercise");
        durationDialogFragment.setDurationListener(new DurationDialogFragment.DurationListener() {
            @Override
            public void onDurationFinished(long duration) {
                //TODO: CONSTANT class?
                mExerciseDuration =  (duration / 1000);
            }
        });
    }

    /**
     * Check that a duration has been inputted before creating exercise object.
     *
     * @param inputtedDuration user inputted duration from dialogFragment
     * @return true if a duration has been entered | false if duration is empty
     */
    private boolean isDurationInputted(long inputtedDuration) {
        if (inputtedDuration == 0) {
            Context context = getApplicationContext();
            Toast dialogToast = Toast.makeText(context, "Enter a duration", Toast.LENGTH_SHORT);
            dialogToast.show();
            return false;
        } else {
            return true;
        }
    }

}