/*
 * Author: Dominic Kennedy
 * Purpose: Test Room db is working correctly.
 */

package com.dk.workouttimer;

import android.content.Context;

import com.dk.workouttimer.database.AppDatabase;
import com.dk.workouttimer.database.WorkoutDao;
import com.dk.workouttimer.models.Exercise;
import com.dk.workouttimer.models.Workout;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RoomDbInstrumentedTest {
    private WorkoutDao workoutDao;
    private AppDatabase db;

    /**
     * Init db and dao before testing.
     */
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        workoutDao = db.workoutDao();
    }

    /**
     * Close db after testing.
     */
    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Test that workout data that is written to the db is the same when retrieved as list.
     */
    @Test
    public void writeAndReadDb() {
        Exercise e1 = new Exercise("exercise1", 10);
        ArrayList<Exercise> arrayList = new ArrayList<>();
        arrayList.add(e1);

        Workout workout = new Workout("title", 10, 1, arrayList);
        workoutDao.insertWorkout(workout);
        List<Workout> workoutList = workoutDao.loadAllWorkouts();

        assertEquals(workoutList.get(0).getTitle(), workout.getTitle());
        assertEquals(workoutList.get(0).getNoExercises(), workout.getNoExercises());
        assertEquals(workoutList.get(0).getTotalDuration(), workout.getTotalDuration());

        assertEquals(workoutList.get(0).getExerciseList().get(0).getDuration(), workout.getExerciseList().get(0).getDuration());
        assertEquals(workoutList.get(0).getExerciseList().get(0).getName(),workout.getExerciseList().get(0).getName());
    }

    /**
     * Test that a workout can be deleted from the db.
     */
    @Test
    public void deleteWorkoutTest() {
        Exercise e1 = new Exercise("exercise1", 10);
        Exercise e2 = new Exercise("exercise2", 4);
        Exercise e3 = new Exercise("exercise3", 5);

        ArrayList<Exercise> arrayList1 = new ArrayList<>();
        arrayList1.add(e1);
        arrayList1.add(e2);
        arrayList1.add(e3);

        ArrayList<Exercise> arrayList2 = new ArrayList<>();
        arrayList2.add(e2);
        arrayList2.add(e3);

        Workout workout1 = new Workout("workout1", 19, 3, arrayList1);
        workoutDao.insertWorkout(workout1);

        Workout workout2 = new Workout("workout2", 9, 2, arrayList2);
        workoutDao.insertWorkout(workout2);

        Workout workout3 = new Workout("workout3", 9, 2,arrayList2);
        workoutDao.insertWorkout(workout3);

        workoutDao.deleteWorkout(workout2);

        List<Workout> workoutList = workoutDao.loadAllWorkouts();

        assertNotSame(workoutList.get(1).getTitle(), workout2.getTitle());
    }

}
