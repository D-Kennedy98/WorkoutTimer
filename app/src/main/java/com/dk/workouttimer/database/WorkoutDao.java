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

@Dao
public interface WorkoutDao {

    /**
     * Insert workout to db.
     * @param workout Workout object to be inserted to db.
     */
    @Insert
    void insertWorkout(Workout workout);

    /**
     * Deletes workout object from db.
     * @param workout Workout object to be deleted.
     */
    @Delete
    void deleteWorkout(Workout workout);

    /**
     * Returns all workout objects stored in db.
     * @return List of all workout objects.
     */
    @Query("SELECT * FROM workoutTable ORDER BY id")
    List<Workout> loadAllWorkouts();


//    /**
//     * Deletes all workout objects from db table.
//     */
//    @Query("DELETE FROM workoutTable")
//    void clearTable();

//    /**
//     * Updates stored workout object params.
//     * If object doesn't exist in db, then db is not updated.
//     * @param workout Workout object to be updated.
//     */
//    @Update
//    void updateWorkout(Workout workout);


}