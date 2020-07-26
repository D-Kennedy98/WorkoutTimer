/*
 * Author: Dominic Kennedy 160304253
 * Purpose: Defines workout class used to store exercise data
 */

package com.dk.workouttimer.models;

import android.os.Parcelable;
import android.os.Parcel;

public class Exercise implements Parcelable {
    private String mName;
    private long mDuration;

    public Exercise(String mName, long mDuration) {
        this.mName = mName;
        this.mDuration = mDuration;
    }

    // implements parcelable so that exercise objects can be passed as intent
    private Exercise(Parcel in) {
        mName = in.readString();
        mDuration = in.readLong();
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
        dest.writeLong(mDuration);
    }

    public String getName() {
        return mName;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

}