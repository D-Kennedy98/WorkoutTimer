/*
 * Author: Dominic Kennedy
 * Purpose: Provide methods to convert time duration in seconds/milliseconds
 * to the format mm:ss for cleaner UI display.
 */

package com.dk.workouttimer;

import java.util.Locale;

public class TimeFormatConverter {

    /**
     * Convert time remaining in millis to format ss:mm.
     *
     * @param time Time being converted in milli seconds.
     * @return Time in string format of ss:mm.
     */
    public String convertMilliTime(long time) {
        int mins = (int) (time / 1000) / 60;
        int secs = (int) (time / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
    }

    /**
     * Convert time in seconds to format ss:mm.
     *
     * @param time Time being converted.
     * @return String of time in format ss:mm.
     */
    public String convertSecondsTime(long time) {
        int mins = (int) time / 60;
        int secs = (int) time % 60;
        return  String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
    }

}