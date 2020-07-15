/*
 Author: Dominic Kennedy 160304253
 Purpose: Implements Timer screen which displays the workout timer
 */

package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "";
    private Button mStartPauseBtn;
    private boolean mIsTimerRunning;
    private CountDownTimer mTimer;
    private int mDuration;

    private TextView mTimerValueTxt;
    private TextView mNextExerciseTxt;
    private TextView mNextExerciseTitleTxt;

    private ArrayList<Workout> workoutArrayList;

    static int tickCount;

    /**
     * keep count of onFinish calls to access correct WO array object
     */
    static int finCount;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // retrieve duration intent
//        final Intent mainIntent = getIntent();
//        Workout workout = mainIntent.getParcelableExtra("workout");
//        mDuration = workout.getDuration() * 1000;


        /*
            new implementation
         */

        final Intent workoutIntent = getIntent();
        workoutArrayList = workoutIntent.getParcelableArrayListExtra("workoutList");
        mDuration = workoutArrayList.get(0).getDuration();





        // init views
        mTimerValueTxt = findViewById(R.id.timer_value);
        mNextExerciseTxt = findViewById(R.id.next_exercise);
        mNextExerciseTitleTxt = findViewById(R.id.next_exercise_title);
        TextView workoutText = findViewById(R.id.workout_title);
        mStartPauseBtn = findViewById(R.id.pause_btn);
        Button restartBtn = findViewById(R.id.restart_btn);
        ImageView mBackBtn = findViewById(R.id.back_btn);

        // set views
        mStartPauseBtn.setText(R.string.pause);
      //  workoutText.setText(workout.getExercise());

        startCountdown(mDuration);

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
        mTimer = new CountDownTimer(mDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                updateTimerTxt(millisUntilFinished);
                updateNextExerciseTxt();
                tickCount++;
                Log.i(TAG, "tickCount: " + tickCount);



            }


            // TODO: get duration from appropriate array element
            //       check for last array element
            @Override
            public void onFinish() {

                finCount++;
                Log.i(TAG, "FinCount: " + finCount);

                // if there are still WO objs in array, start next CD
                if(finCount < workoutArrayList.size()) {
                    startCountdown(workoutArrayList.get(finCount).getDuration());
                } else {
                    mTimerValueTxt.setTextSize(70);
                    mTimerValueTxt.setText(R.string.workout_complete);
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

    // format time to mm:ss and set value of timer
    private void updateTimerTxt(long timeRemaining) {
        int mins = (int) (timeRemaining / 1000) / 60;
        int secs = (int) (timeRemaining / 1000) % 60;
        String formTimeRemaining = String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
        mTimerValueTxt.setText(formTimeRemaining);
    }

    private void updateNextExerciseTxt() {
        int arrayIndex = finCount + 1;

        if(arrayIndex < workoutArrayList.size()) {
            String exercise = workoutArrayList.get(arrayIndex).getExercise();
            mNextExerciseTxt.setText(exercise);
        } else  {
            mNextExerciseTxt.setText(R.string.final_exercise);
        }

    }


}