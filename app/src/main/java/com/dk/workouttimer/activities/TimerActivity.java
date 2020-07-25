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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    // TODO: - int -> long
    //  -Include workout name in UI

    private static final String TAG = "";
    private Button mStartPauseBtn;
    private boolean mIsTimerRunning;
    private CountDownTimer mTimer;
    private int mDuration;

    private TextView mTimerValueTxt;
    private TextView mCurrentExerciseTxt;
    private TextView mNextExerciseTxt;
    private TextView mNextExerciseTitleTxt;

    /**
     * stores milliseconds to seconds conversion
     */
    private static final int CONVERT_MILLIS = 1000;

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
    private static int finCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // get arrayList of workout objects to be passed to CDT
        final Intent workoutIntent = getIntent();
        exerciseArrayList = workoutIntent.getParcelableArrayListExtra("exerciseArrayList");
        mDuration = exerciseArrayList.get(0).getDuration();

        Log.i(TAG, String.valueOf(exerciseArrayList.size()));

        // init views
        mTimerValueTxt = findViewById(R.id.timer_value);
        mCurrentExerciseTxt = findViewById(R.id.current_exercise_title);
        mNextExerciseTxt = findViewById(R.id.next_exercise);
        mNextExerciseTitleTxt = findViewById(R.id.next_exercise_title);
        TextView workoutText = findViewById(R.id.workout_title);
        mStartPauseBtn = findViewById(R.id.pause_btn);
        Button restartBtn = findViewById(R.id.restart_btn);
        ImageView mBackBtn = findViewById(R.id.back_btn);

        // set views
        mStartPauseBtn.setText(R.string.pause);
        //  workoutText.setText(workout.getName());

        startCountdown(mDuration * CONVERT_MILLIS);

        // set on click listeners
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finCount = 0;
                finish();
            }
        });

//        mStartPauseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mIsTimerRunning) {
//                    startCountdown();
//                    mStartPauseBtn.setText(R.string.pause);
//                } else {
//                    pauseTimer();
//                }
//            }
//        });

//        restartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finCount = 0;
//                finish();
//                startActivity(workoutIntent);
//            }
//        });

    }

    /**
     *  create count down timer
     *  TODO: Fix delay before starting after first time causes time to jump, could add name of exercise?
     */

    private void startCountdown(int mDuration) {
        mTimer = new CountDownTimer(mDuration, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerTxt(millisUntilFinished);
                updateCurrentExerciseTxt();
                updateNextExerciseTxt();
                tickCount++;
                Log.i(TAG, "tickCount: " + tickCount);
            }

            @Override
            public void onFinish() {
                finCount++;
                Log.i(TAG, "FinCount: " + finCount);

                // if there are still WO objs in array, start next CD
                if (finCount < exerciseArrayList.size()) {
                    startCountdown(exerciseArrayList.get(finCount).getDuration() * CONVERT_MILLIS);
                } else {
                    mTimerValueTxt.setTextSize(70);
                    mTimerValueTxt.setText(R.string.workout_complete);
                    mCurrentExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
                }
            }
        }.start();

        mIsTimerRunning = true;
    }

    private void pauseTimer() {
        mTimer.cancel();
        mIsTimerRunning = false;
        mStartPauseBtn.setText(R.string.start);
    }

    /**
     * format time to mm:ss and set value of timer
     */
    private void updateTimerTxt(long timeRemaining) {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        String formTimeRemaining = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTimerValueTxt.setText(formTimeRemaining);
    }

    /**
     * update the textView to display the current exercise
     */
    private void updateCurrentExerciseTxt() {
        if (finCount < exerciseArrayList.size()) {
            String currentExercise = exerciseArrayList.get(finCount).getName();
            mCurrentExerciseTxt.setText(currentExercise);
        }
    }

    /**
     * update the textView to display upcoming exercise
     */
    private void updateNextExerciseTxt() {
        int arrayIndex = finCount + 1;

        if (arrayIndex < exerciseArrayList.size()) {
            String nextExercise = exerciseArrayList.get(arrayIndex).getName();
            mNextExerciseTxt.setText(nextExercise);
        } else {
            mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
            mNextExerciseTxt.setText(R.string.final_exercise);
            mNextExerciseTxt.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

}