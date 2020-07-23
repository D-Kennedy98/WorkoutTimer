package com.dk.workouttimer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WorkoutDao {

    @Insert
    void insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("SELECT * FROM workoutTable ORDER BY id")
    List<Workout> loadAllWorkouts();

    @Query("DELETE FROM workoutTable")
    void clearTable();

}

