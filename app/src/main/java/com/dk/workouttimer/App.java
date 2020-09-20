/*
Author: Dominic Kennedy
Purpose: Create db and data access object before the first activity so that
they can be accessed by all components.
*/

package com.dk.workouttimer;

import android.app.Application;

import com.dk.workouttimer.database.AppDatabase;
import com.dk.workouttimer.database.WorkoutDao;

public class App extends Application {

    /**
     * Db for storing workout objects
     */
    public AppDatabase database;

    /**
     * Workout dao for db interactions
     */
    public WorkoutDao workoutDao;

    /**
     * Called when app is launched. Instantiates db and dao.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getInstance(this);
        workoutDao = database.workoutDao();
    }

}