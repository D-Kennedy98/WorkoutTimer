/*
 * Author: Dominic Kennedy
 * Purpose: define dao interface for interacting with com.dk.workouttimer.database
 */

package com.dk.workouttimer.database;

import com.dk.workouttimer.models.Workout;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WorkoutDao {

    /**
     * insert workout to db
     * @param workout object to be inserted to db
     */
    @Insert
    void insertWorkout(Workout workout);

    /**
     * updates stored workout object params
     * if object doesn't exist in db, then db is not updated
     * @param workout object to be updated
     */
    @Update
    void updateWorkout(Workout workout);

    /**
     * deletes workout object from db
     * @param workout object to be deleted
     */
    @Delete
    void deleteWorkout(Workout workout);

    /**
     * returns all workout objects stored in db
     * @return list of all workouts
     */
    @Query("SELECT * FROM workoutTable ORDER BY id")
    List<Workout> loadAllWorkouts();

    /**
     * deletes all workout objects from table
     */
    @Query("DELETE FROM workoutTable")
    void clearTable();

}