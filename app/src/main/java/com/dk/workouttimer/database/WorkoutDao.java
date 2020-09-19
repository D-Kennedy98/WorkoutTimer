/*
Author: Dominic Kennedy
Purpose: Define data access object interface for interacting with database.
*/

package com.dk.workouttimer.database;

import com.dk.workouttimer.models.Workout;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkoutDao {

    /**
     * Insert workout to db.
     *
     * @param workout Workout object to be inserted to db.
     */
    @Insert
    void insertWorkout(Workout workout);

    /**
     * Deletes workout object from db.
     *
     * @param workout Workout object to be deleted.
     */
    @Delete
    void deleteWorkout(Workout workout);

    /**
     * Returns all workout objects stored in db as list.
     *
     * @return List of all workout objects.
     */
    @Query("SELECT * FROM workoutTable ORDER BY id")
    List<Workout> loadAllWorkouts();

}