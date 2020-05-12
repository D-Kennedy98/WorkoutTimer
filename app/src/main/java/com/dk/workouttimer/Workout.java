// Author: Dominic Kennedy 160304253

package com.dk.workouttimer;

import android.os.Parcelable;
import android.os.Parcel;

public class Workout implements Parcelable {
    private String mExercise;
    private int mIconResource;
    private int mImageResource;
    private String mCategory;
    private int mDuration;

    private Workout(Parcel in) {
        mExercise = in.readString();
        mCategory = in.readString();
        mDuration = in.readInt();
        mImageResource = in.readInt();
    }

    Workout(String mExercise, int mIconResource, int mImageResource, String mCategory) {
        this.mExercise = mExercise;
        this.mIconResource = mIconResource;
        this.mImageResource = mImageResource;
        this.mCategory = mCategory;
        this.mDuration = 0;
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
        dest.writeString(mCategory);
        dest.writeInt(mDuration);
        dest.writeInt(mImageResource);
    }

    String getExercise() {
        return mExercise;
    }

    int getIconResource() {
        return mIconResource;
    }

    int getImageResource() {
        return mImageResource;
    }

    String getCategory() {
        return mCategory;
    }

    int getDuration() {
        return mDuration;
    }

    void setDuration(int duration) {
        this.mDuration = duration;
    }

}