/*
 * Author: Dominic Kennedy
 * Purpose: Implements home screen where users choose a workout
 */

package com.dk.workouttimer.activities;

import android.content.Intent;
import android.os.Bundle;
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
        Exercise e1 = new Exercise("e1", 7);
        Exercise e2 = new Exercise("e2", 5);
        Exercise e3 = new Exercise("e3", 10);
        Exercise e4 = new Exercise("e4", 4);

        mExerciseList1.add(e1);
        mExerciseList1.add(e2);
        mExerciseList1.add(e3);
        mExerciseList2.add(e3);
        mExerciseList2.add(e4);

        Workout workout1 = new Workout("Workout 1", 10, 10, mExerciseList1);
        Workout workout2 = new Workout("Workout 2", 15, 30, mExerciseList2);
        Workout workout3 = new Workout("Workout 3", 5, 1, mExerciseList2);

        // database operations
        final App app = (App)getApplication();
//        app.workoutDao.clearTable();

        app.workoutDao.insertWorkout(workout1);
        app.workoutDao.insertWorkout(workout2);
        app.workoutDao.insertWorkout(workout3);

        mWorkoutArrayList = (ArrayList<Workout>) app.workoutDao.loadAllWorkouts();

        // log retried workouts and their associated exercises
//        for (int i = 0; i < mWorkoutArrayList.size(); i++) {
//            Log.i("workout", mWorkoutArrayList.get(i).getTitle());
//
//            for (int j = 0; j < mWorkoutArrayList.get(i).getExerciseList().size(); j++) {
//                Log.i("Exercise", mWorkoutArrayList.get(i).getExerciseList().get(j).getName());
//                Log.i("Duration", String.valueOf(mWorkoutArrayList.get(i).getExerciseList().get(j).getDuration()));
//
//            }
//        }

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
     * Set up UI views.
     * TODO: Refactor
     */
    private void setViews() {

        // init views
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView infoBtn = findViewById(R.id.info_btn);

        // set the recycler view
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mWorkoutArrayList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, 2, GridLayoutManager.VERTICAL, false);
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
     * Launch timer for the workout chosen on recycler view.
     */
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) view.getTag();
            Workout workout = mWorkoutArrayList.get(holder.getAdapterPosition());
           // Log.i("Workout Clicked RV", workout.getTitle());
            launchTimerActivity((ArrayList<Exercise>) workout.getExerciseList());
        }
    };

    /**
     * Launch timer activity to time chosen workout.
     * @param exerciseArrayList stores exercise data to pass to CDT
     */
    private void launchTimerActivity(ArrayList<Exercise> exerciseArrayList) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putParcelableArrayListExtra("exerciseArrayList", exerciseArrayList);
        startActivity(intent);
    }

    /**
     * Launch information activity which contains info about the app.
     */
    public void launchInformationActivity() {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    /**
     * Launch createWorkout activity to create new workout routines.
     */
    public void launchCreateWorkoutActivity() {
        Intent intent = new Intent(this, CreateWorkoutActivity.class);
        startActivity(intent);
    }

}