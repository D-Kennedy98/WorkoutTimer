/*
 Author: Dominic Kennedy 160304253
 Purpose: Implements Timer screen which displays the workout timer
 */

package com.dk.workouttimer.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.workouttimer.R;
import com.dk.workouttimer.models.Exercise;
import com.dk.workouttimer.models.Workout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "";
    private Button mStartPauseBtn;
    private boolean mIsTimerRunning;
    private CountDownTimer mTimer;
    private TextView mTimerValueTxt;
    private TextView mCurrentExerciseTxt;
    private TextView mNextExerciseTxt;
    private TextView mNextExerciseTitleTxt;
    private Button mStopBtn;

    /**
     * stores milliseconds to seconds conversion
     */
    private static final int CONVERT_MILLIS_SECONDS = 1000;

    /**
     * stores interval between onTick calls in milliseconds
     */
    private static final long COUNT_DOWN_INTERVAL = 1000;

    /**
     * stores arrayList of exercises from workouts activity
     */
    private ArrayList<Exercise> exerciseArrayList;

    /**
     * keep count of onTick calls for logging
     */
    private static int tickCount;

    /**
     * keep count of onFinish calls to access correct WO array object
     */
    private static int onFinishCount;

    private long firstDuration;

    /**
     * Store the time remaining of current timer to be passed
     * into CDT constructor
     */
    private long millisRemaining;

    /**
     * Store if timer is on penultimate exercise.
     */
    private boolean mIsPenultimateExercise;

    /**
     * Store if onFinish has been called.
     */
    private boolean mIsWorkoutFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // init views
        TextView mWorkoutTitleTxt = findViewById(R.id.workout_title_timer);
        mTimerValueTxt = findViewById(R.id.timer_value);
        mCurrentExerciseTxt = findViewById(R.id.current_exercise_title);
        mNextExerciseTxt = findViewById(R.id.next_exercise);
        mNextExerciseTitleTxt = findViewById(R.id.next_exercise_title);
        mStartPauseBtn = findViewById(R.id.pause_btn);
        mStopBtn = findViewById(R.id.stop_btn);
        ImageView mBackBtn = findViewById(R.id.back_btn);

        mStartPauseBtn.setText(R.string.start);


        // get workout object from WO activity intent
        final Intent workoutIntent = getIntent();
        Workout workout = workoutIntent.getParcelableExtra("workout");
        mWorkoutTitleTxt.setText(workout.getTitle());
        exerciseArrayList = (ArrayList<Exercise>) workout.getExerciseList();

        firstDuration = exerciseArrayList.get(0).getDuration();

        // set timer value to duration of first exercise
        updateTimerTxt(firstDuration * CONVERT_MILLIS_SECONDS);

        // set exercise text to first exercise
        updateCurrentExerciseTxt();

        updateNextExerciseTxt();

        // update to duration of first exercise for passing to StartCountdown
        millisRemaining = (firstDuration * CONVERT_MILLIS_SECONDS);


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinishCount = 0;
                finish();
            }
        });

        mStartPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsTimerRunning) {
                    startCountdown(millisRemaining);
                    mStartPauseBtn.setText(R.string.pause);
                } else {
                    pauseTimer();
                }
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

    }

    /**
     *  Create count down timer.
     *  TODO: Fix delay before starting after first time causes time to jump, could add name of exercise?
     */

    private void startCountdown(long duration) {
        mTimer = new CountDownTimer(duration, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerTxt(millisUntilFinished);
                updateCurrentExerciseTxt();
                updateNextExerciseTxt();
                tickCount++;
                Log.i(TAG, "tickCount: " + tickCount);
                millisRemaining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                onFinishCount++;
                Log.i(TAG, "FinCount: " + onFinishCount);

                // if there are still exercise objects in array, start next CD
                if (onFinishCount < exerciseArrayList.size()) {
                    startCountdown(exerciseArrayList.get(onFinishCount).getDuration() * CONVERT_MILLIS_SECONDS);
                } else {
                    mIsWorkoutFinished = true;
                    mTimer.cancel();
                    mTimerValueTxt.setTextSize(70);
                    mTimerValueTxt.setText(R.string.workout_complete);
                    mCurrentExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
                    mStartPauseBtn.setVisibility(View.INVISIBLE);
                    mStopBtn.setText(R.string.restart);
                }
            }
        }.start();

        mIsTimerRunning = true;
    }

    /**
     * Pause timer.
     */
    private void pauseTimer() {
        if (mIsTimerRunning) {
            mTimer.cancel();
        }
        mIsTimerRunning = false;
        mStartPauseBtn.setText(R.string.start);
    }

    /**
     * Stop timer and reset UI.
     */
    private void stopTimer() {
        onFinishCount = 0;
        pauseTimer();
        millisRemaining = firstDuration * CONVERT_MILLIS_SECONDS;
        updateTimerTxt(firstDuration * CONVERT_MILLIS_SECONDS);
        updateCurrentExerciseTxt();
        updateNextExerciseTxt();

        // if stopped on penultimate exercise reset UI
        if (mIsPenultimateExercise && !mIsWorkoutFinished) {
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setTypeface(mNextExerciseTxt.getTypeface());
            mIsPenultimateExercise = false;

        } else if (mIsWorkoutFinished) {   // if workout has finished reset UI
            mTimerValueTxt.setTextSize(130);
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setTypeface(Typeface.DEFAULT);
            mCurrentExerciseTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setVisibility(View.VISIBLE);
            mStartPauseBtn.setVisibility(View.VISIBLE);
            mIsWorkoutFinished = false;
            mIsPenultimateExercise = false;
        }
    }


    /**
     * Format time to mm:ss and set value of timer.
     */
    private void updateTimerTxt(long timeRemaining) {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        String formTimeRemaining = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTimerValueTxt.setText(formTimeRemaining);
    }

    /**
     * Update the textView to display the current exercise.
     */
    private void updateCurrentExerciseTxt() {
        if (onFinishCount < exerciseArrayList.size()) {
            String currentExercise = exerciseArrayList.get(onFinishCount).getName();
            mCurrentExerciseTxt.setText(currentExercise);
        }
    }

    /**
     * Update the textView to display upcoming exercise.
     */
    private void updateNextExerciseTxt() {
        int arrayIndex = onFinishCount + 1;

        if (arrayIndex < exerciseArrayList.size()) {
            String nextExercise = exerciseArrayList.get(arrayIndex).getName();
            mNextExerciseTxt.setText(nextExercise);
        } else {
            mIsPenultimateExercise = true;
            mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
            mNextExerciseTxt.setText(R.string.final_exercise);
            // mNextExerciseTxt.setTypeface(Typeface.DEFAULT_BOLD);
            // unsure how to change back to normal typeface
        }
    }

}