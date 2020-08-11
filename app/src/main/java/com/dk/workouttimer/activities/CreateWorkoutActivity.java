/*
 * Author: Dominic Kennedy
 * Purpose: Allow user to create exercises which are used
 * to create workout objects.
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
     * Application class.
     */
    private App app;

    // TODO: appropriate max lengths
    /**
     * Store max length of workout title.
     */
    private static final int MAX_EXERCISE_NAME_LENGTH = 30;

    /**
     * Store max length of workout (3 hours) in milliseconds.
     */
    private static final int MAX_TOTAL_WORKOUT_DURATION = 10800000;

    /**
     * Store max length that of exercise name.
     */
    private static final int MAX_WORKOUT_TITLE_LENGTH = 35;

    /**
     * Store duration entered in duration dialog fragment.
     */
    private long mExerciseDuration;

    /**
     * Store total duration of all exercises for ensuring max duration isn't exceeded.
     */
    private long mTotalDuration;

    /**
     * Store workout title to be passed into workout constructor.
     */
    private String mWorkoutTitle;

    /**
     * Store exercise objects that make up a workout.
     * Passed as intent back to workout activity to populate recycler view.
     */
    private ArrayList<Exercise> mExerciseArrayList = new ArrayList<>();

    /**
     * Edit text elements for inputting workout title and exercise name.
     */
    private EditText mWorkoutTitleInput;
    private EditText mNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        app = (App)getApplication();

        setUpViews();

        setUpAddExerciseBtn();
        setUpDurationBtn();
        setUpSaveBtn();
        setUpBackBtn();

    }

    /**
     * Set up UI views.
     */
    private void setUpViews() {
        mWorkoutTitleInput = findViewById(R.id.workout_title_input);
        mNameInput = findViewById(R.id.name_input);
    }

    /**
     * Create new exercise object from edit text values and add to array list.
     */
    private void setUpAddExerciseBtn() {
        Button mAddExerciseBtn = findViewById(R.id.add_exercise_btn);

        mAddExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getNameInput();

                if (isValidNameInput(name) && isDurationValid(mExerciseDuration)) {
                    mExerciseArrayList.add(new Exercise(name, mExerciseDuration));

                    Context context = getApplicationContext();
                    Toast addToast = Toast.makeText(context, R.string.exercise_added, Toast.LENGTH_SHORT);
                    addToast.show();

                    mNameInput.getText().clear();
                    mExerciseDuration = 0;
                }
            }
        });
    }

    /**
     * Set up duration button which opens a duration dialog fragment
     * for inputting exercise duration.
     */
    private void setUpDurationBtn() {
        Button mDurationBtn = findViewById(R.id.duration_btn);

        mDurationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationFragment();

            }
        });
    }

    /**
     * Set up save button which creates a workout object,
     * adds it to the database then launches workouts activity.
     */
    private void setUpSaveBtn() {
        Button mSaveBtn = findViewById(R.id.save_btn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidWorkoutTitleInput(getWorkoutTitleInput())
                        && isValidExerciseArray()) {
                    Workout newWorkout = new Workout(
                            mWorkoutTitle, (int) calcTotalDuration(mExerciseArrayList),
                            mExerciseArrayList.size(), mExerciseArrayList);

                    app.workoutDao.insertWorkout(newWorkout);
                    launchWorkoutsActivity();
                }
            }
        });
    }

    /**
     * Set up back button which navigates user back to
     * workouts activity without saving.
     * TODO: Confirmation btn
     */
    private void setUpBackBtn() {
        ImageView mBackBtn = findViewById(R.id.back_btn_cw);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWorkoutsActivity();
            }
        });
    }

    /**
     * Get workout title input from edit text.
     *
     * @return Workout title input as string or empty string if input is null.
     */
    private String getWorkoutTitleInput() {
        if (mWorkoutTitleInput.getText() == null) {
            return "";
        } else {
            return mWorkoutTitleInput.getText().toString().trim();
        }
    }

    /**
     * Check workout title input is valid.
     *
     * @return True if title is valid. | False if title is empty or exceeds max length.
     */
    private Boolean isValidWorkoutTitleInput(String title) {
        // Check title has been entered.
        if (title.isEmpty()) {
            Context contextWO = getApplicationContext();
            Toast titleToast = Toast.makeText(contextWO, R.string.enter_workout_title, Toast.LENGTH_SHORT);
            titleToast.show();
            return false;

          // Check title doesn't exceed max length.
        } else if (title.length() > MAX_WORKOUT_TITLE_LENGTH) {
            Context contextWO = getApplicationContext();
            Toast nameToast = Toast.makeText(contextWO, R.string.workout_title_exceed, Toast.LENGTH_SHORT);
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
     * @return True if not empty. | False if empty.
     */
    private Boolean isValidExerciseArray() {
        if (mExerciseArrayList.size() != 0) {
            return true;
        } else {
            Context contextExArray = getApplicationContext();
            Toast ExArrayToast = Toast.makeText(contextExArray, R.string.workout_contain_exercise, Toast.LENGTH_SHORT);
            ExArrayToast.show();
            return false;
        }
    }

    /**
     * Get exercise name input from edit text.
     *
     * @return Name input from edit text as string or empty string if input is null.
     */
    private String getNameInput() {
        if (mNameInput.getText() == null) {
            return "";
        } else  {
            return mNameInput.getText().toString().trim();
        }
    }

    /**
     * Check if inputted name is valid.
     *
     * @return True is name is valid. | False if name is empty or exceeds max length.
     */
    private Boolean isValidNameInput(String name) {
        // Check name has been entered.
        if (name.isEmpty()) {
            Context contextEx = getApplicationContext();
            Toast nameToast = Toast.makeText(contextEx, R.string.enter_exercise_name, Toast.LENGTH_SHORT);
            nameToast.show();
            return false;

          // Check name doesn't exceed max length.
        } else if (name.length() > MAX_EXERCISE_NAME_LENGTH) {
            Context contextEx = getApplicationContext();
            Toast nameToast = Toast.makeText(contextEx, R.string.exercise_name_exceed, Toast.LENGTH_SHORT);
            nameToast.show();
            return false;

        } else {
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
     * @param exerciseArrayList Stores the exercise objects which will be assigned to workout field.
     * @return Total duration of all exercises in workout.
     */
    private long calcTotalDuration(ArrayList<Exercise> exerciseArrayList) {
        long totalDuration = 0;

        for(int i = 0; i < exerciseArrayList.size(); i++) {
            totalDuration += exerciseArrayList.get(i).getDuration();
        }

        return totalDuration;
    }

    /**
     * Show dialog fragment to allow exercise duration to be inputted.
     *
     * TODO: Look at depreciated tags
     */
    private void showDurationFragment() {
        DurationDialogFragment durationDialogFragment = new DurationDialogFragment();
        durationDialogFragment.show(getFragmentManager(), "exercise");

        // Implement DurationListener interface to retrieve duration from fragment.
        durationDialogFragment.setDurationListener(new DurationDialogFragment.DurationListener() {
            @Override
            public void onDurationFinished(long duration) {
            mExerciseDuration =  (duration / 1000);
            mTotalDuration += duration;
            }
        });
    }

    /**
     * Check that exercise duration is valid.
     *
     * @param inputDuration User inputted duration from dialogFragment.
     * @return True if a valid duration has been entered.
     *        | False if duration is empty or workout duration exceeds 3 hours.
     */
    private boolean isDurationValid(long inputDuration) {
        // Check a duration has been inputted.
        if (inputDuration == 0) {
            Context durationContext = getApplicationContext();
            Toast durationToast = Toast.makeText(durationContext, R.string.enter_duration, Toast.LENGTH_SHORT);
            durationToast.show();
            return false;

            // Check workout duration doesn't exceed 3 hours.
        }  else if (mTotalDuration > MAX_TOTAL_WORKOUT_DURATION) {
            Context durationContext = getApplicationContext();
            Toast durationToast = Toast.makeText(durationContext, R.string.total_duration_exceeds, Toast.LENGTH_LONG);
            durationToast.show();
            return false;

           // Notify user if total duration has been met.
        } else if (mTotalDuration == MAX_TOTAL_WORKOUT_DURATION) {
            Context durationContext = getApplicationContext();
            Toast durationToast = Toast.makeText(durationContext, R.string.max_duration_met, Toast.LENGTH_LONG);
            durationToast.show();
            return true;
        }

        // All checks pass.
        else {
            return true;
        }

    }

}