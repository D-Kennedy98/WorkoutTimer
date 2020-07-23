/*
 * Author: Dominic Kennedy 160304253
 * Purpose: Defines workout class used to store exercise data
 */

package com.dk.workouttimer.models;

import android.os.Parcelable;
import android.os.Parcel;

public class Exercise implements Parcelable {
    private String mName;
    private int mDuration;

    public Exercise(String mName, int mDuration) {
        this.mName = mName;
        this.mDuration = mDuration * 1000;
    }

    // implements parcelable so that exercise objects can be passed as intent
    private Exercise(Parcel in) {
        mName = in.readString();
        mDuration = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mDuration);
    }

    public String getName() {
        return mName;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration * 1000;
    }

}