/*
 * Author: Dominic Kennedy
 * Purpose: Defines workout object which is used to store data about a list of exercises
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

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mTitle;
    private int mTotalDuration;
    private int mNoExercises;

    @TypeConverters(Converter.class)
    private List<Exercise> mExerciseList;

    @Ignore
    public Workout(String mTitle, int mTotalDuration, int mNoExercises, List<Exercise> mExerciseList) {
        this.mTitle = mTitle;
        this.mTotalDuration = mTotalDuration;
        this.mNoExercises = mNoExercises;
        this.mExerciseList = mExerciseList;
    }

    // constructor for room db
    public Workout(int id, String mTitle, int mTotalDuration, int mNoExercises, List<Exercise> mExerciseList) {
        this.id = id;
        this.mTitle = mTitle;
        this.mTotalDuration = mTotalDuration;
        this.mNoExercises = mNoExercises;
        this.mExerciseList = mExerciseList;
    }

    // implement parcelable so that the workout objects can be passed as intent
    private Workout(Parcel in) {
        mTitle = in.readString();
        mTotalDuration = in.readInt();
        mNoExercises = in.readInt();
        mExerciseList = in.createTypedArrayList(Exercise.CREATOR);
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mTotalDuration);
        dest.writeInt(mNoExercises);
        dest.writeTypedList(mExerciseList);
    }

    /**
     * getters / setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getTotalDuration() {
        return mTotalDuration;
    }

    public void setTotalDuration(int mTotalDuration) {
        this.mTotalDuration = mTotalDuration;
    }

    public int getNoExercises() {
        return mNoExercises;
    }

    public void setNoExercises(int mNoExercises) {
        this.mNoExercises = mNoExercises;
    }

    public List<Exercise> getExerciseList() {
        return mExerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.mExerciseList = exerciseList;
    }


}