/*
 * Author: Dominic Kennedy
 * Date: 22/07/20
 * Purpose: Defines the abstract database.
 *          Follows singleton DP - Only one instance can exist
 */
package com.dk.workouttimer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * singleton instance
     */
    private static AppDatabase sInstance = null;

    public static final String NAME = "workout-DB";

    /**
     * Default constructor.
     */
    public AppDatabase(){}

    /**
     * get instance of the created database
     * @param context current state of application
     * @return AppDatabase instance
     * TODO: change so it doesn't run on main thread
     */
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, NAME).allowMainThreadQueries().build();

        }
        return sInstance;
    }

    public abstract WorkoutDao workoutDao();
}
