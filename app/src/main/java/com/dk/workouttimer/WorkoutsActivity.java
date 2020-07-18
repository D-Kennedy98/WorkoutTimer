/*
Author: Dominic Kennedy 160304243
Purpose: Implements home screen where users choose a workout
*/
package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsActivity extends AppCompatActivity {


    private ArrayList<Exercise> mExerciseList = new ArrayList<>();


    /**
     * Tags for logging
     */
    private static final String WO_TITLE_TAG = "Workout Title";
    private static final String EX_NAME_TAG = "Exercise names";
    private static final String EX_LIST_COUNT_TITLE = "ExerciseList WO Title";

    private ArrayList<Workout> mWorkoutArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("OnCreate", "called");


        if(savedInstanceState != null) {

            onRestoreInstanceState(savedInstanceState);
        }

        // create workout data and add to list
        mExerciseList.add(new Exercise("Press Ups", 3));
        mExerciseList.add(new Exercise("Plank", 5));
        mExerciseList.add(new Exercise("High Knees", 4));


        // default workout
        mWorkoutArrayList.add(new Workout("title 1", 10, 1, mExerciseList));


        // init views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView infoBtn = findViewById(R.id.info_btn);

        // set the recycler view
       // RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mExerciseList);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mWorkoutArrayList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        // set on click listeners
        recyclerAdapter.setOnItemClickListener(onItemClickListener);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchInformationActivity();
            }
        });


        TextView workouts = findViewById(R.id.workout_title);
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateWorkoutActivity();
            }
        });


        // get workout object from CreateWOAct
        Intent createWorkoutIntent = getIntent();
        Workout newWorkout = createWorkoutIntent.getParcelableExtra("workout");

        // only access intent if != null i.e after navigating back from CreateWOAct once a WO has been created
        if(newWorkout != null) {

            Log.i(WO_TITLE_TAG, newWorkout.getTitle());
            // log if WO intent is successfully passed
            for(int i = 0; i < newWorkout.getExerciseArrayList().size(); i++) {
                Log.i(EX_NAME_TAG, newWorkout.getExerciseArrayList().get(i).getName());
            }

            mWorkoutArrayList.add(newWorkout);

            Log.i(EX_LIST_COUNT_TITLE, mWorkoutArrayList.get(0).getTitle());
        }

    }

    // click on individual recycler view items to launch workout activity
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            Exercise exercise = mExerciseList.get(holder.getAdapterPosition());
            launchWorkoutActivity(exercise);
        }
    };

    /**
     * Used in initial implementation
     * @param exercise stores chosen workout data
     */
    private void launchWorkoutActivity(Exercise exercise) {
        Intent intent = new Intent(this, WActivity.class);
        intent.putExtra("workout", exercise);
        startActivity(intent);
    }


    private void launchTimerActivity(ArrayList mWorkoutList) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workoutList", mWorkoutList);
        startActivity(intent);
    }

    public void launchInformationActivity() {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    public void launchCreateWorkoutActivity() {
        Intent intent = new Intent(this, CreateWorkoutActivity.class);
        startActivity(intent);
    }

}