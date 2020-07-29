/*
 * Author: Dominic Kennedy
 * Purpose: Implements home screen where users choose a workout
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
import com.dk.workouttimer.models.Workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsActivity extends AppCompatActivity implements RecyclerAdapter.OnWorkoutListener {

    /**
     * Stores workouts retrieved from db.
     */
    private ArrayList<Workout> mWorkoutArrayList = new ArrayList<>();

    /**
     * Recycler adapter to bind workout data set to recycler view
     */
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        setViews();

        setWorkoutOnClick();

        setInfoOnClick();

    }

    /**
     * Set up UI views.
     * TODO: Refactor
     */
    private void setViews() {
        // load workouts from database and add to array list
        App app = (App)getApplication();
        mWorkoutArrayList = (ArrayList<Workout>) app.workoutDao.loadAllWorkouts();

        // initialise recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // set the recycler view
        recyclerAdapter = new RecyclerAdapter(
                this, mWorkoutArrayList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * Set onClickListener for information button.
     */
    public void setInfoOnClick() {
        ImageView infoBtn = findViewById(R.id.info_btn);

        // Launch information activity.
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchInformationActivity();
            }
        });
    }

    /**
     * Set onClickListener ****
     * TODO: Create create btn
     */
    public void setWorkoutOnClick() {
        TextView workouts = findViewById(R.id.workout_title);

        workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateWorkoutActivity();
            }
        });
    }

    /**
     * Launch timer activity for chosen workout.
     *
     * @param chosenWorkout workout object containing exercises information
     */
    private void launchTimerActivity(Workout chosenWorkout) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", chosenWorkout);
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

    /**
     * Start timer activity for chosen workout.
     *
     * @param position position of view holder chosen on recycler view in index workout array list.
     */
    @Override
    public void onStartTimerClick(int position) {
        Log.i("On StartTimer", "Click");
        Workout chosenWorkout = mWorkoutArrayList.get(position);
        launchTimerActivity(chosenWorkout);
        //launchTimerActivity((ArrayList<Exercise>) chosenWorkout.getExerciseList());
    }

    /**
     * Delete chosen workout.
     *
     * @param position position of view holder chosen on recycler view to index workout array list.
     */
    @Override
    public void onDeleteClick(int position) {
        Workout chosenWorkout = mWorkoutArrayList.get(position);

        // remove from database
        App app = (App)getApplication();
        app.workoutDao.deleteWorkout(chosenWorkout);

        // remove from array list
        mWorkoutArrayList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }

}