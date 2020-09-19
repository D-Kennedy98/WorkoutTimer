/*
Author: Dominic Kennedy
Purpose: Implement TimeDurationPickerDialogFragment methods for inputting exercise duration.
*/

package com.dk.workouttimer.fragment;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class DurationDialogFragment extends TimeDurationPickerDialogFragment {

    /**
     * Interface to pass duration to CreateWO activity from the dialogFragment.
     */
    public interface DurationListener {
        void onDurationFinished(long durationMillis);
    }

    /**
     * Listener variable to be assigned implementation of interface.
     */
    private DurationListener mDurationListener;

    /**
     * Used to set duration listener to dialogFragment object.
     *
     * @param durationListener Listener that the fragment is set to.
     */
    public void setDurationListener(DurationListener durationListener) {
        this.mDurationListener = durationListener;
    }

    /**
     * Set the unit to be displayed by dialogFragment.
     *
     * @return Time unit value.
     */
    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.MM_SS;
    }

    /**
     * Called when user leaves dialogFragment by pressing OK button.
     * OnDurationListener is triggered to pass duration back
     * to CreateWO activity.
     *
     * @param view Picker view.
     * @param durationMillis Duration that was entered.
     */
    @Override
    public void onDurationSet(TimeDurationPicker view, long durationMillis) {
            mDurationListener.onDurationFinished(durationMillis);
    }

}