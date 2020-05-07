package com.dk.workouttimer;

import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {
    private String exercise;
    private int duration;

    private Workout(Parcel in) {
        exercise = in.readString();
        duration = in.readInt();
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

    Workout(String exercise, int duration) {
        this.exercise = exercise;
        this.duration = duration;
    }

    Workout() {
        exercise = "run";
        duration = 10;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exercise);
        dest.writeInt(duration);
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}