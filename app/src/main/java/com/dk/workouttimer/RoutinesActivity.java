/*
Author: Dominic Kennedy 160304243
Purpose: Implements home screen where users choose a workout
*/
package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoutinesActivity extends AppCompatActivity {

    private ArrayList<Workout> mWorkoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create workout data and add to list
        mWorkoutList.add(new Workout("Press Ups", 3));
        mWorkoutList.add(new Workout("Plank", 5));
        mWorkoutList.add(new Workout("High Knees", 4));


        // init views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView infoBtn = findViewById(R.id.info_btn);

        // set the recycler view
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mWorkoutList);
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


        // New implementation

        TextView workouts = findViewById(R.id.workout_title);
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateWorkoutActivity();
            }
        });



    }

    // click on individual recycler view items to launch workout activity
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            Workout workout = mWorkoutList.get(holder.getAdapterPosition());
            launchWorkoutActivity(workout);
        }
    };

    /**
     * Used in initial implementation
     * @param workout stores chosen workout data
     */
    private void launchWorkoutActivity(Workout workout) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("workout", workout);
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