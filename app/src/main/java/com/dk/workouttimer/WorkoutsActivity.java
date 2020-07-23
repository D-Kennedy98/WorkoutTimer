/*
Author: Dominic Kennedy 160304243
Purpose: Implements home screen where users choose a workout
*/
package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsActivity extends AppCompatActivity {


    private ArrayList<Exercise> mExerciseList1 = new ArrayList<>();
    private ArrayList<Exercise> mExerciseList2 = new ArrayList<>();


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

        // Create data
        Exercise e1 = new Exercise("e1", 50);
        Exercise e2 = new Exercise("e2", 100);
        Exercise e3 = new Exercise("e3", 25);
        Exercise e4 = new Exercise("e4", 75);

        mExerciseList1.add(e1);
        mExerciseList1.add(e2);
        mExerciseList2.add(e3);
        mExerciseList2.add(e4);

        Workout workout1 = new Workout("Workout 1", 5, 10, mExerciseList1);
        Workout workout2 = new Workout("Workout 2", 15, 30, mExerciseList2);


        App app = (App)getApplication();
        app.workoutDao.clearTable();

        app.workoutDao.insertWorkout(workout1);
        app.workoutDao.insertWorkout(workout2);

        List<Workout> workoutList = app.workoutDao.loadAllWorkouts();
        Log.i("List size", String.valueOf(workoutList.size()));
        for (Workout workout: workoutList) {
            Log.i("workout", workout.getTitle());
            for(int i = 0; i < workoutList.size(); i++) {
               Log.i("Exercise",workout.getExerciseList().get(i).getName());
            }
        }



        // init views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView infoBtn = findViewById(R.id.info_btn);

        // set the recycler view
       // RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mExerciseList);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, (ArrayList<Workout>) workoutList);

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



    }

    // click on individual recycler view items to launch workout activity
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            Exercise exercise = mExerciseList1.get(holder.getAdapterPosition());
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


    private void launchTimerActivity() {
        Intent intent = new Intent(this, TimerActivity.class);
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