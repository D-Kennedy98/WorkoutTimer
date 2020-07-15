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

public class MainActivity extends AppCompatActivity {

    private ArrayList<Workout> mWorkoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create workout data and add to list
        mWorkoutList.add(new Workout("Press Ups",R.drawable.ic_press_up, R.drawable.press_up, "Arms / Chest", 3));
        mWorkoutList.add(new Workout("Plank",R.drawable.ic_plank, R.drawable.plank, "Core", 5));
        mWorkoutList.add(new Workout("High Knees",R.drawable.ic_high_knees, R.drawable.high_knees, "Cardio", 4));


//        mWorkoutList.add(new Workout("Yoga", R.drawable.ic_yoga, R.drawable.yoga, "Full Body / Stretch", ));
//        mWorkoutList.add(new Workout("Burpees", R.drawable.ic_burpees, R.drawable.burpees, "Cardio"));
//        mWorkoutList.add(new Workout("Squats", R.drawable.ic_squat, R.drawable.squat, "Legs"));

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
                launchTimerActivity(mWorkoutList);
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

}