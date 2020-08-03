/*
 * Author: Dominic Kennedy
 * Purpose: Implement TimeDurationPickerDialogFragment methods for inputting exercise duration
 *          Define DurationListener interface to pass data from fragment to CreateWO activity
 */

package com.dk.workouttimer.fragment;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class DurationDialogFragment extends TimeDurationPickerDialogFragment {

    /**
     * interface to pass duration to CreateWO activity from the dialogFragment
     */
    public interface DurationListener {
        void onDurationFinished(long duration);
    }

    /**
     * listener variable to be assigned implementation of interface
     */
    private DurationListener mDurationListener;

    /**
     * Used to set duration listener to dialogFragment object.
     * @param durationListener listener that the fragment is set to
     */
    public void setDurationListener(DurationListener durationListener) {
        this.mDurationListener = durationListener;
    }

    /**
     * Set the unit to be displayed by dialogFragment.
     * @return time unit value
     */
    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.MM_SS;
    }

    /**
     * Called when user leaves dialogFragment by pressing OK button.
     * OnDurationListener is triggered to pass duration back
     * to CreateWO activity
     * @param view picker view
     * @param duration duration that was entered
     */
    @Override
    public void onDurationSet(TimeDurationPicker view, long duration) {
            mDurationListener.onDurationFinished(duration);
    }

}