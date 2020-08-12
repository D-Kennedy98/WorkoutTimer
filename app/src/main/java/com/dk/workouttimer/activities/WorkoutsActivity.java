/*
 * Author: Dominic Kennedy
 * Purpose: Implements home screen activity where a user can
 * choose a workout to start or delete a workout.
 */

package com.dk.workouttimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dk.workouttimer.App;
import com.dk.workouttimer.R;
import com.dk.workouttimer.adapter.RecyclerAdapter;
import com.dk.workouttimer.models.Workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsActivity extends AppCompatActivity implements RecyclerAdapter.OnWorkoutListener {

    /**
     * Application class.
     */
    private App app;

    /**
     * Stores workouts retrieved from db.
     */
    private ArrayList<Workout> mWorkoutArrayList = new ArrayList<>();

    /**
     * Recycler adapter to bind workout data set to recycler view.
     */
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        app = (App)getApplication();

        // Access db on background thread
        AccessDbRunnable runnable = new AccessDbRunnable();
        new Thread(runnable).start();

      //  mWorkoutArrayList = getDatabaseWorkouts();

      //  setUpRecyclerView();

        setWorkoutOnClick();

        setInfoOnClick();

    }

    /**
     * Get array list of workout objects from db.
     */
    private ArrayList<Workout> getDatabaseWorkouts() {
        return (ArrayList<Workout>) app.workoutDao.loadAllWorkouts();
    }

    /**
     * Delete workout from db.
     *
     * @param workout Workout to removed from db.
     */
    private void deleteDatabaseWorkout(Workout workout) {
        app.workoutDao.deleteWorkout(workout);
    }

    /**
     * Initialise recycler view.
     */
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

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
    private void setInfoOnClick() {
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
    private void setWorkoutOnClick() {
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
     * @param chosenWorkout Workout object containing exercises information.
     */
    private void launchTimerActivity(Workout chosenWorkout) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("workout", chosenWorkout);
        startActivity(intent);
    }

    /**
     * Launch information activity which contains info about the app.
     */
    private void launchInformationActivity() {
        Intent intent = new Intent(this, InformationActivity.class);
        startActivity(intent);
    }

    /**
     * Launch createWorkout activity to create new workout routines.
     */
    private void launchCreateWorkoutActivity() {
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
        Workout chosenWorkout = mWorkoutArrayList.get(position);
        launchTimerActivity(chosenWorkout);
    }

    /**
     * Remove chosen workout from database and recycler data set.
     *
     * @param position Position of view holder chosen on recycler view to index workout array list.
     */
    @Override
    public void onDeleteClick(int position) {
        Workout chosenWorkout = mWorkoutArrayList.get(position);

        // remove from db async
        DeleteDbRunnable runnable = new DeleteDbRunnable(chosenWorkout);
        new Thread(runnable).start();

        // remove from workout array list
        mWorkoutArrayList.remove(position);

        // update recycler view
        recyclerAdapter.notifyItemRemoved(position);
    }

    /**
     * Runnable inner class to access workouts from db.
     */
    private class AccessDbRunnable implements Runnable {

        /**
         * Get workout list from db on background thread to pass into recycler adaptor.
         */
        @Override
        public void run() {
           mWorkoutArrayList = getDatabaseWorkouts();
           setUpRecyclerView();
        }
    }

    /**
     * Runnable inner class to delete workout from db.
     */
    private class DeleteDbRunnable implements Runnable {

        /**
         * Workout to be deleted from db.
         */
        Workout workout;

        /**
         * Runnable constructor.
         *
         * @param workout Workout chosen to be deleted.
         */
        DeleteDbRunnable(Workout workout) {
            this.workout = workout;
        }

        /**
         * Delete workout from db on background thread.
         */
        @Override
        public void run() {
            deleteDatabaseWorkout(workout);
        }
    }


}