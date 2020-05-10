package com.dk.workouttimer;

import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {
    private String mExercise;
    private int mIconResource;
    private int mDuration;

    private Workout(Parcel in) {
        mExercise = in.readString();
        mDuration = in.readInt();
    }

    // TODO: Better way of setting initial dur?
    Workout(String mExercise, int mIconResource, int mDuration) {
        this.mExercise = mExercise;
        this.mIconResource = mIconResource;
        this.mDuration = mDuration;
    }

    Workout() {
        mExercise = "run";
        mDuration = 10;
    }

    /** implements parcelable **/
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
        dest.writeString(mExercise);
        dest.writeInt(mDuration);
    }

    public String getExercise() {
        return mExercise;
    }

    public void setExercise(String mExercise) {
        this.mExercise = mExercise;
    }

    public int getIconResource() {
        return mIconResource;
    }

    public void setIconResource(int iconResource) {
        this.mIconResource = iconResource;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

}