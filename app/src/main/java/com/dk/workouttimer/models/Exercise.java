/*
Author: Dominic Kennedy 160304253
Purpose: Defines workout objects used to store exercise data.
*/

package com.dk.workouttimer.models;

import android.os.Parcelable;
import android.os.Parcel;

public class Exercise implements Parcelable {

    /**
     * Name of exercise.
     */
    private String mName;

    /**
     * Duration of exercise in seconds.
     */
    private long mDuration;

    /**
     * Exercise constructor.
     *
     * @param mName Name of exercise.
     * @param mDuration Duration of exercise in seconds.
     */
    public Exercise(String mName, long mDuration) {
        this.mName = mName;
        this.mDuration = mDuration;
    }

    /**
     * Write exercise instance to parcel.
     *
     * @param in Parcel in which object should be written.
     */
    private Exercise(Parcel in) {
        mName = in.readString();
        mDuration = in.readLong();
    }

    /**
     * Interface require for parcelable.
     */
    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {

        /**
         * Generates new instances of parcelable exercise class.
         *
         * @param in The parcel to read object data from.
         * @return Return new parcelable exercise instance.
         */
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        /**
         * Create new array of parcelable exercise class.
         *
         * @param size Size of array.
         * @return Returns array of parcelable exercise class.
         */
        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    /**
     * Describes contents of parcelable exercise instance.
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
     * @param dest The parcel in which the exercise object should be written.
     * @param flags Additional flags about how the exercise object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeLong(mDuration);
    }

    /**
     * Get name field of exercise instance.
     *
     * @return Name field of exercise instance.
     */
    public String getName() {
        return mName;
    }

    /**
     * Get duration field of exercise instance.
     *
     * @return Duration field of exercise instance.
     */
    public long getDuration() {
        return mDuration;
    }

}