/*
 * Author: Dominic Kennedy
 * Purpose: Defines the abstract database.
 * Follows singleton DP - Only one instance can exist.
 */

package com.dk.workouttimer.database;

import android.content.Context;

import com.dk.workouttimer.models.Workout;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Singleton instance.
     */
    private static AppDatabase sInstance = null;

    /**
     * Database name.
     */
    private static final String NAME = "workout-DB";

    /**
     * Default constructor.
     */
     AppDatabase(){}

    /**
     * Get instance of the created database.
     *
     * @param context Current state of application.
     * @return AppDatabase instance.
     */
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, NAME).build();
        }
        return sInstance;
    }

    /**
     * WorkoutDAO interface.
     */
    public abstract WorkoutDao workoutDao();

}