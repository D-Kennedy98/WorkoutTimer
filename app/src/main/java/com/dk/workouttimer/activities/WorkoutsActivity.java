/*
Author: Dominic Kennedy 160304243
Purpose: Implements home screen where users choose a workout
*/

package com.dk.workouttimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.workouttimer.App;
import com.dk.workouttimer.R;
import com.dk.workouttimer.adapters.RecyclerAdapter;
import com.dk.workouttimer.models.Exercise;
import com.dk.workouttimer.models.Workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsActivity extends AppCompatActivity {

    private ArrayList<Exercise> mExerciseList1 = new ArrayList<>();
    private ArrayList<Exercise> mExerciseList2 = new ArrayList<>();

    /**
     * stores workouts retrieved from db
     */
    private ArrayList<Workout> mWorkoutArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        // create data
        Exercise e1 = new Exercise("e1", 10);
        Exercise e2 = new Exercise("e2", 5);
        Exercise e3 = new Exercise("e3", 25);
        Exercise e4 = new Exercise("e4", 75);

        mExerciseList1.add(e1);
        mExerciseList1.add(e2);
        mExerciseList1.add(e3);
        mExerciseList2.add(e3);
        mExerciseList2.add(e4);

        Workout workout1 = new Workout("Workout 1", 5, 10, mExerciseList1);
        Workout workout2 = new Workout("Workout 2", 15, 30, mExerciseList2);

        // com.dk.workouttimer.database operations
        App app = (App)getApplication();
        app.workoutDao.clearTable();

        app.workoutDao.insertWorkout(workout1);
        app.workoutDao.insertWorkout(workout2);

        mWorkoutArrayList = (ArrayList<Workout>) app.workoutDao.loadAllWorkouts();
        Log.i("List size", String.valueOf(mWorkoutArrayList.size()));
        for (Workout workout: mWorkoutArrayList) {
            Log.i("workout", workout.getTitle());
            for(int i = 0; i < mWorkoutArrayList.size(); i++) {
               Log.i("Exercise",workout.getExerciseList().get(i).getName());
            }
        }


        setViews();

        TextView workouts = findViewById(R.id.workout_title);
        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateWorkoutActivity();
            }
        });

    }

    /**
     * set up UI views
     */
    private void setViews() {

        // init views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView infoBtn = findViewById(R.id.info_btn);

        // set the recycler view
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mWorkoutArrayList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        // set on click listener
        recyclerAdapter.setOnItemClickListener(onItemClickListener);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchInformationActivity();
            }
        });
    }

    /**
     * launch timer for the workout chosen on recycler view
     */
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) view.getTag();
            Workout workout = mWorkoutArrayList.get(holder.getAdapterPosition());
            Log.i("Workout Clicked RV", workout.getTitle());
            launchTimerActivity((ArrayList<Exercise>) workout.getExerciseList());
        }
    };

    /**
     * launch timer activity to time chosen workout
     * @param exerciseArrayList stores exercise data to pass to CDT
     */
    private void launchTimerActivity(ArrayList<Exercise> exerciseArrayList) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putParcelableArrayListExtra("exerciseArrayList", exerciseArrayList);
        startActivity(intent);
    }

    /**
     * launch information activity which contains info about the app
     */
    public void launchInformationActivity() {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    /**
     * launch createWorkout activity to create new workout routines
     */
    public void launchCreateWorkoutActivity() {
        Intent intent = new Intent(this, CreateWorkoutActivity.class);
        startActivity(intent);
    }

}