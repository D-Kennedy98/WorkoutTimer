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

    /**
     * Stores milliseconds to seconds conversion.
     */
    private static final int CONVERT_MILLIS_SECONDS = 1000;

    /**
     * Stores interval between onTick calls in milliseconds.
     */
    private static final long COUNT_DOWN_INTERVAL = 1000;

    /**
     * TAG for logging.
     */
    private static final String TAG = "";

    /**
     * Keep count of onFinish calls to access correct WO array object.
     */
    private static int sOnFinishCount;

    /**
     * Store if timer is running.
     */
    private boolean mIsTimerRunning;

    /**
     * Store if timer is on penultimate exercise.
     */
    private boolean mIsPenultimateExercise;

    /**
     * Store if onFinish has been called.
     */
    private boolean mIsWorkoutFinished;

    /**
     * Store if SoundPool.play has been called.
     */
    private boolean mIsAlarmPlaying;

    /**
     * Store alarm resource id.
     */
    private int mAlarmSound;

    /**
     * Store duration of first exercise.
     */
    private long mFirstDuration;

    /**
     * Store the time remaining of current timer to be passed
     * into CDT constructor
     */
    private long mMillisRemaining;

    /**
     * Stores arrayList of exercises from workouts activity.
     */
    private ArrayList<Exercise> mExerciseArrayList;

    /**
     * CountDownTimer object to provide timer functionality.
     */
    private CountDownTimer mTimer;

    /**
     * Manages and plays sound audio resource for alarm sound.
     */
    private SoundPool mSoundPool;

    /**
     * Workout object retrieved from WOs activity which stores
     * data regarding the workout and exercises.
     */
    private Workout workout;

    /**
     * Text views.
     */
    private TextView mCurrentExerciseTxt;
    private TextView mNextExerciseTxt;
    private TextView mNextExerciseTitleTxt;
    private TextView mTimerValueTxt;

    /**
     * Buttons to start/pause or stop the timer.
     */
    private Button mStartPauseBtn;
    private Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Get workout object from WOs activity intent.
        final Intent workoutIntent = getIntent();
        workout = workoutIntent.getParcelableExtra("workout");

        // Get data from WO object.
        mExerciseArrayList = (ArrayList<Exercise>) workout.getExerciseList();
        mFirstDuration = mExerciseArrayList.get(0).getDuration();

        // Update to duration of first exercise for passing to StartCountdown.
        mMillisRemaining = (mFirstDuration * CONVERT_MILLIS_SECONDS);

        setUpViews();
        setUpStartPauseBtn();
        setUpStopBtn();
        setUpBackBtn();

        setUpAlarm();
        mIsAlarmPlaying = false;

    }

    /**
     * Initialise UI views.
     */
    private void setUpViews() {
        TextView mWorkoutTitleTxt = findViewById(R.id.workout_title_timer);
        mCurrentExerciseTxt = findViewById(R.id.current_exercise_title);
        mNextExerciseTxt = findViewById(R.id.next_exercise);
        mNextExerciseTitleTxt = findViewById(R.id.next_exercise_title);
        mTimerValueTxt = findViewById(R.id.timer_value);

        // Set text using data from workout object.
        mWorkoutTitleTxt.setText(workout.getTitle());
        updateCurrentExerciseTxt();
        updateNextExerciseTxt();

        // Set timer value to duration of first exercise.
        mTimerValueTxt.setText(convertTime(mFirstDuration * CONVERT_MILLIS_SECONDS));
    }

    /**
     * Set up startPause button which starts/pauses timer.
     */
    private void setUpStartPauseBtn() {
        mStartPauseBtn = findViewById(R.id.pause_btn);

        mStartPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsTimerRunning) {
                    startCountdown(mMillisRemaining);
                    mStartPauseBtn.setText(R.string.pause);
                } else {
                    pauseTimer();
                }
            }
        });
    }

    /**
     * Set up stop button which stops and restarts timer.
     */
    private void setUpStopBtn() {
        mStopBtn = findViewById(R.id.stop_btn);

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    /**
     * Set up back button which navigates user back
     * to workout activity.
     */
    private void setUpBackBtn() {
        ImageView mBackBtn = findViewById(R.id.back_btn);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOnFinishCount = 0;
                finish();
            }
        });
    }

    /**
     * Create count down timer object.
     *
     * @param duration Duration of the timer.
     * TODO: Fix delay before starting after first time causes time to jump, could add name of exercise?
     */
    private void startCountdown(long duration) {
        mTimer = new CountDownTimer(duration, COUNT_DOWN_INTERVAL) {

            /**
             * Callback fired after each count down interval.
             * @param millisUntilFinished The amount of time left until countdown is finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerValueTxt.setText(convertTime(millisUntilFinished));
                updateCurrentExerciseTxt();
                updateNextExerciseTxt();
                mMillisRemaining = millisUntilFinished;
                //sTickCount++;
                //Log.i(TAG, "tickCount: " + sTickCount);
            }

            /**
             * Callback fired when timer is finished (millisUntilFinished = 0).
             */
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
     * Stop timer.
     */
    private void stopTimer() {
        sOnFinishCount = 0;
        pauseTimer();
        mMillisRemaining = mFirstDuration * CONVERT_MILLIS_SECONDS;
        mTimerValueTxt.setText(convertTime(mFirstDuration * CONVERT_MILLIS_SECONDS));
        updateCurrentExerciseTxt();
        updateNextExerciseTxt();
        mStopBtn.setText(R.string.stop);

        // If stopped on penultimate exercise and there's more than one exercise, reset UI.
        if (mIsPenultimateExercise && (mExerciseArrayList.size() > 1)) {
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mIsPenultimateExercise = false;

        // If workout is finished reset UI and stop alarm.
        } else if (mIsWorkoutFinished && (mExerciseArrayList.size() > 1)) {
            mIsWorkoutFinished = false;
            mTimerValueTxt.setTextSize(130);
            mNextExerciseTitleTxt.setVisibility(View.VISIBLE);
            mCurrentExerciseTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setVisibility(View.VISIBLE);
            mStartPauseBtn.setVisibility(View.VISIBLE);
            mSoundPool.pause(mAlarmSound);

        // If workout is finished and there's only 1 exercise, reset UI and stop alarm.
        } else if (mIsWorkoutFinished && (mExerciseArrayList.size() == 1)) {
            mIsWorkoutFinished = false;
            mTimerValueTxt.setTextSize(130);
            mCurrentExerciseTxt.setVisibility(View.VISIBLE);
            mNextExerciseTxt.setVisibility(View.VISIBLE);
            mStartPauseBtn.setVisibility(View.VISIBLE);
            mSoundPool.pause(mAlarmSound);
        }
    }

    /**
     * Update the textView to display the current exercise.
     */
    private void updateCurrentExerciseTxt() {
        if (sOnFinishCount < mExerciseArrayList.size()) {
            mCurrentExerciseTxt.setText(mExerciseArrayList.get(sOnFinishCount).getName());
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

        mAlarmSound = mSoundPool.load(this, R.raw.alarm, 1);
    }

    /**
     * Play alarm sound for when timer is complete.
     */
    private void playAlarm() {
        if (!mIsAlarmPlaying) {
            mSoundPool.play(mAlarmSound, 1, 1, 1, 0, 2);
            mIsAlarmPlaying = true;

           /* If alarm has been paused after a workout is completed,
            * then the exercise is restarted and completed again,
            * the alarm must be resumed (not played).
            */
        } else {
            mSoundPool.resume(mAlarmSound);
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

    /**
     * Convert time remaining in millis to format ss:mm.
     * Implements TimeConverter interface.
     *
     * @param time Time being converted in milli seconds.
     * @return Time in string format of ss:mm.
     */
    private String convertTime(long time) {
        int mins = (int) (time / 1000) / 60;
        int secs = (int) (time / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
    }

}