/*
 Author: Dominic Kennedy 160304253
 Purpose: Implements Timer screen which displays the workout timer
 */

package com.dk.workouttimer.activities;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
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
    private CountDownTimer mTimer;
    private TextView mTimerValueTxt;
    private TextView mCurrentExerciseTxt;
    private TextView mNextExerciseTxt;
    private TextView mNextExerciseTitleTxt;
    private Button mStopBtn;
    private boolean mIsTimerRunning;


    /**
     * Stores milliseconds to seconds conversion.
     */
    private static final int CONVERT_MILLIS_SECONDS = 1000;

    /**
     * Stores interval between onTick calls in milliseconds.
     */
    private static final long COUNT_DOWN_INTERVAL = 1000;

    /**
     * Stores arrayList of exercises from workouts activity.
     */
    private ArrayList<Exercise> mExerciseArrayList;

    /**
     * Keep count of onTick calls for logging
     */
    private static int sTickCount;

    /**
     * Keep count of onFinish calls to access correct WO array object.
     */
    private static int sOnFinishCount;

    /**
     * Store duration of first exercise.
     */
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

    /**
     * Manages and plays sound audio resource for alarm sound.
     */
    private SoundPool mSoundPool;

    /**
     * Store alarm resource id.
     */
    private static int sAlarmSound;

    /**
     * Store if SoundPool .play has been called.
     */
    private static boolean sIsAlarmPlaying;


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
        mExerciseArrayList = (ArrayList<Exercise>) workout.getExerciseList();

        firstDuration = mExerciseArrayList.get(0).getDuration();

        // set timer value to duration of first exercise
        updateTimerTxt(firstDuration * CONVERT_MILLIS_SECONDS);

        // set exercise text to first exercise
        updateCurrentExerciseTxt();

        updateNextExerciseTxt();

        // update to duration of first exercise for passing to StartCountdown
        millisRemaining = (firstDuration * CONVERT_MILLIS_SECONDS);

        sIsAlarmPlaying = false;


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOnFinishCount = 0;
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

        setUpAlarm();

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
                sTickCount++;
                Log.i(TAG, "tickCount: " + sTickCount);
                millisRemaining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                sOnFinishCount++;
                Log.i(TAG, "FinCount: " + sOnFinishCount);

                // if there are still exercise objects in array, start next CD
                if (sOnFinishCount < mExerciseArrayList.size()) {
                    startCountdown(mExerciseArrayList.get(sOnFinishCount).getDuration() * CONVERT_MILLIS_SECONDS);
                } else {
                    mIsWorkoutFinished = true;
                    mIsPenultimateExercise = false;
                    mTimer.cancel();
                    mTimerValueTxt.setTextSize(70);
                    mTimerValueTxt.setText(R.string.workout_complete);
                    mCurrentExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTxt.setVisibility(View.INVISIBLE);
                    mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
                    mStartPauseBtn.setVisibility(View.INVISIBLE);
                    mStopBtn.setText(R.string.restart);

                    playAlarm();
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
        sOnFinishCount = 0;
        pauseTimer();
        millisRemaining = firstDuration * CONVERT_MILLIS_SECONDS;
        updateTimerTxt(firstDuration * CONVERT_MILLIS_SECONDS);
        updateCurrentExerciseTxt();
        updateNextExerciseTxt();
        mStopBtn.setText(R.string.stop);

        // if stopped on penultimate exercise and there's more than one exercise, reset UI
        if (mIsPenultimateExercise && (mExerciseArrayList.size() > 1)) {
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mIsPenultimateExercise = false;

        // if workout is finished reset UI and stop alarm
        } else if (mIsWorkoutFinished && (mExerciseArrayList.size() > 1)) {
            mIsWorkoutFinished = false;
            mTimerValueTxt.setTextSize(130);
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mCurrentExerciseTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setVisibility(View.VISIBLE);
            mStartPauseBtn.setVisibility(View.VISIBLE);
            mSoundPool.pause(sAlarmSound);

        // if workout is finished and there's only 1 exercise, reset UI and stop alarm
        } else if (mIsWorkoutFinished && (mExerciseArrayList.size() == 1)) {
            mIsWorkoutFinished = false;
            mTimerValueTxt.setTextSize(130);
            mCurrentExerciseTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setVisibility(View.VISIBLE);
            mStartPauseBtn.setVisibility(View.VISIBLE);
            mSoundPool.pause(sAlarmSound);
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
        if (sOnFinishCount < mExerciseArrayList.size()) {
            String currentExercise = mExerciseArrayList.get(sOnFinishCount).getName();
            mCurrentExerciseTxt.setText(currentExercise);
        }
    }

    /**
     * Update the textView to display upcoming exercise.
     */
    private void updateNextExerciseTxt() {
        int arrayIndex = sOnFinishCount + 1;

        if (arrayIndex < mExerciseArrayList.size()) {
            String nextExercise = mExerciseArrayList.get(arrayIndex).getName();
            mNextExerciseTxt.setText(nextExercise);
        } else {
            mIsPenultimateExercise = true;
            mNextExerciseTitleTxt.setVisibility(View.INVISIBLE);
            mNextExerciseTxt.setText(R.string.final_exercise);
            // mNextExerciseTxt.setTypeface(Typeface.DEFAULT_BOLD);
            // unsure how to change back to normal typeface
        }
    }

    /**
     * Build SoundPool and load in alarm sound.
     */
    private void setUpAlarm() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        sAlarmSound = mSoundPool.load(this, R.raw.alarm, 1);
    }

    /**
     * Play alarm sound for when timer is complete.
     */
    private void playAlarm() {
        if (!sIsAlarmPlaying) {
            mSoundPool.play(sAlarmSound, 1, 1, 1, 0, 2);
            sIsAlarmPlaying = true;

           /* if alarm has been paused after a workout is completed, then the exercise is restarted
            * and completed again, the alarm must be resumed (not played).
            */
        } else {
            mSoundPool.resume(sAlarmSound);
        }
    }

    /**
     * Release soundPool when activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoundPool.release();
        mSoundPool = null;
    }

}