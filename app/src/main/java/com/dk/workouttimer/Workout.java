/*
Author: Dominic Kennedy 160304253
Purpose: Defines workout class used to store data
*/

package com.dk.workouttimer;

import android.os.Parcelable;
import android.os.Parcel;

public class Workout implements Parcelable {
    private String mExercise;
//    private String mCategory;
    private int mDuration;

    private Workout(Parcel in) {
        mExercise = in.readString();
     //   mCategory = in.readString();
        mDuration = in.readInt();
    }

    Workout(String mExercise, int mDuration) {
        this.mExercise = mExercise;
     //   this.mCategory = mCategory;
        this.mDuration = mDuration * 1000;
    }

    // implements parcelable
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
      //  dest.writeString(mCategory);
        dest.writeInt(mDuration);
    }

    String getExercise() {
        return mExercise;
    }

//    String getCategory() {
//        return mCategory;
//    }

    int getDuration() {
        return mDuration;
    }

    void setDuration(int duration) {
        this.mDuration = duration * 1000;
    }

}