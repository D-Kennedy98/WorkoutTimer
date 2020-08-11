/*
 * Author: Dominic Kennedy
 * Purpose: Defines workout object which is used to store data about a list of exercises.
 * Implements parcelable interface so that workout instances can be passed as intent.
 */

package com.dk.workouttimer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.dk.workouttimer.database.Converter;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "workoutTable")
public class Workout implements Parcelable {

    /**
     * Field automatically assigned to workout instance when written to db.
     * Also acts as unique primary key.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Title of the workout.
     */
    private String mTitle;

    /**
     * Total duration of all the exercises contained in exercise list field.
     */
    private int mTotalDuration;

    /**
     * Number of exercises contained in exercise list field.
     */
    private int mNoExercises;

    /**
     * List of exercises that make up the workout.
     * Type converter is used for converting list of exercises to strings
     * for storing in db and back to a list when retrieving from db.
     */
    @TypeConverters(Converter.class)
    private List<Exercise> mExerciseList;

    /**
     * Workout constructor.
     *
     * @param mTitle Title of workout.
     * @param mTotalDuration Total duration of all exercises of the workout.
     * @param mNoExercises Number of exercises in the workout.
     * @param mExerciseList List of exercises included in workout.
     */
    @Ignore
    public Workout(String mTitle, int mTotalDuration, int mNoExercises, List<Exercise> mExerciseList) {
        this.mTitle = mTitle;
        this.mTotalDuration = mTotalDuration;
        this.mNoExercises = mNoExercises;
        this.mExerciseList = mExerciseList;
    }

    /**
     * Workout constructor for Room db.
     *
     * @param id Id primary key allocated for workout objects.
     * @param mTitle Title of workout.
     * @param mTotalDuration Total duration of all exercises of the workout.
     * @param mNoExercises Number of exercises in the workout.
     * @param mExerciseList List of exercises included in workout.
     */
    public Workout(int id, String mTitle, int mTotalDuration, int mNoExercises, List<Exercise> mExerciseList) {
        this.id = id;
        this.mTitle = mTitle;
        this.mTotalDuration = mTotalDuration;
        this.mNoExercises = mNoExercises;
        this.mExerciseList = mExerciseList;
    }

    /**
     * Write workout instance into parcel.
     *
     * @param in Parcel in which object should be written.
     */
    private Workout(Parcel in) {
        mTitle = in.readString();
        mTotalDuration = in.readInt();
        mNoExercises = in.readInt();
        mExerciseList = in.createTypedArrayList(Exercise.CREATOR);
    }

    /**
     * Interface required for parcelable.
     */
    public static final Creator<Workout> CREATOR = new Creator<Workout>() {

        /**
         * Generates new instances of parcelable workout class.
         *
         * @param in The parcel to read object data from.
         * @return Return new parcelable workout instance.
         */
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        /**
         * Create new array of parcelable workout class.
         *
         * @param size Size of array.
         * @return Returns array of parcelable workout class.
         */
        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    /**
     * Describes contents of parcelable workout instance.
     *
     * @return a bitmask indicating the set of special object types marshaled
     *  by this parcelable instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Convert objects to parcel.
     *
     * @param dest The parcel in which the workout object should be written.
     * @param flags Additional flags about how the workout object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mTotalDuration);
        dest.writeInt(mNoExercises);
        dest.writeTypedList(mExerciseList);
    }


    /**
     * Get id field of workout instance.
     *
     * @return Id field of workout instance.
     */
    public int getId() {
        return id;
    }

    /**
     * Set id field of workout instance.
     *
     * @param id Int that id field is set to.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get title field of workout instance.
     *
     * @return Title field of workout instance.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get total duration field of workout instance.
     *
     * @return Total duration field of workout instance.
     */
    public int getTotalDuration() {
        return mTotalDuration;
    }

    /**
     * Get number of exercises field of workout instance.
     *
     * @return Number of exercises field of workout instance.
     */
    public int getNoExercises() {
        return mNoExercises;
    }

    /**
     * Get the list of exercises field of workout instance.
     *
     * @return List of exercises field of workout instance.
     */
    public List<Exercise> getExerciseList() {
        return mExerciseList;
    }



//    /**
//     * Set title field of workout instance.
//     *
//     * @param mTitle String that title field is set to.
//     */
//    public void setTitle(String mTitle) {
//        this.mTitle = mTitle;
//    }

//    /**
//     * Set total duration field of workout instance.
//     *
//     * @param mTotalDuration Int that total duration field is set to.
//     */
//    public void setTotalDuration(int mTotalDuration) {
//        this.mTotalDuration = mTotalDuration;
//    }

//    /**
//     * Set number of exercises field of workout instance.
//     *
//     * @param mNoExercises Int that number of exercises field is set to.
//     */
//    public void setNoExercises(int mNoExercises) {
//        this.mNoExercises = mNoExercises;
//    }

//    /**
//     * Set the list of exercises field of workout instance.
//     *
//     * @param exerciseList Exercise list that field is set to.
//     */
//    public void setExerciseList(List<Exercise> exerciseList) {
//        this.mExerciseList = exerciseList;
//    }


}