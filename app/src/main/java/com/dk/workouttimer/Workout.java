package com.dk.workouttimer;/*
 * Author: Dominic Kennedy
 * Purpose: Defines workout object which is used to store data about a collection of exercises
 *          so that it can be displayed in a list
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Workout implements Parcelable {
    private String mTitle;
    private int mTotalDuration;
    private int mNoExercises;
    private ArrayList<Exercise> mExerciseArrayList;

    public Workout(String mTitle, int mTotalDuration, int mNoExercises, ArrayList<Exercise> mExerciseArrayList) {
        this.mTitle = mTitle;
        this.mTotalDuration = mTotalDuration;
        this.mNoExercises = mNoExercises;
        this.mExerciseArrayList = mExerciseArrayList;
    }

    // implement parcelable so that the workout objects can be passed as intent
    private Workout(Parcel in) {
        mTitle = in.readString();
        mTotalDuration = in.readInt();
        mNoExercises = in.readInt();
        mExerciseArrayList = in.createTypedArrayList(Exercise.CREATOR);
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
        dest.writeTypedList(mExerciseArrayList);
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

    public ArrayList<Exercise> getExerciseArrayList() {
        return mExerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> mExerciseArrayList) {
        this.mExerciseArrayList = mExerciseArrayList;
    }


}
