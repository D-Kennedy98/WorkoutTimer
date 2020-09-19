/*
Author: Dominic Kennedy
Purpose: Implements recycler adapter for creating view holders and binding workout data to them.
*/

package com.dk.workouttimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.workouttimer.R;
import com.dk.workouttimer.utilities.TimeFormatConverter;
import com.dk.workouttimer.models.Workout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    /**
     * Data set binded to adapter.
     */
    private ArrayList<Workout> mWorkoutArrayList;

    /**
     * Instantiates layout XML files to corresponding view objects.
     */
    private LayoutInflater layoutInflater;

    /**
     * OnWorkoutListener interface.
     */
    private OnWorkoutListener mWorkoutListener;

    /**
     * Recycler Adaptor constructor.
     *
     * @param context Current state of app.
     * @param workoutArrayList Data set containing workout data to be bound to view holders.
     * @param workoutListener Allows any object that implements OnWorkoutListener interface to be passed.
     */
    public RecyclerAdapter(Context context, ArrayList<Workout> workoutArrayList, OnWorkoutListener workoutListener) {
        this.mWorkoutArrayList = workoutArrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.mWorkoutListener = workoutListener;
    }

    /**
     * Inflates the grid view and creates view holders which are updated to represent item views.
     * Called as user scrolls when a new item needs to be visible.
     *
     * @param parent View group that new view will be added to after binding to adapter.
     * @param viewType View type of new view.
     * @return View holder representing contents of workout item in the data set.
     */
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.workout_grid_layout, parent, false);
        return new ViewHolder(view, mWorkoutListener);
    }

    /**
     * Binds workout data from array list to view holders.
     *
     * @param holder View holder.
     * @param position Position of view holder.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Workout workout = mWorkoutArrayList.get(position);
        holder.workoutTitle.setText(workout.getTitle());
        holder.duration.setText(String.format(Locale.getDefault(),
                "Duration: %s", TimeFormatConverter.convertSecondsTime(workout.getTotalDuration())));
        holder.numberExercises.setText(String.format(Locale.getDefault(),
                "Exercises: %d", workout.getNoExercises()));
    }

    /**
     * Get number of items in adaptor data set.
     *
     * @return Number of items adaptor data set.
     */
    @Override
    public int getItemCount() {
        return mWorkoutArrayList.size();
    }

    /**
     * Get workout at chosen position on recycler view.
     *
     * @param position Position of view holder.
     * @return Workout at chosen position.
     */
    public Workout getWorkoutAt(int position) {
        return mWorkoutArrayList.get(position);
    }

    /**
     * Represents contents of item in data set. Holders are recycled as user scrolls and
     * new items become visible.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       private TextView workoutTitle, duration, numberExercises;
       private OnWorkoutListener workoutListener;

        ViewHolder(@NonNull View itemView, OnWorkoutListener workoutListener) {
            super(itemView);
            workoutTitle = itemView.findViewById(R.id.workout_txt);
            duration = itemView.findViewById(R.id.workout_duration_txt);
            numberExercises = itemView.findViewById(R.id.number_exercises_txt);
            this.workoutListener = workoutListener;

            // this refers to onClickListener interface
            itemView.setOnClickListener(this);
        }

        /**
         * Get position of item in adapter when clicked to start timer activity
         * for that workout.
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            workoutListener.onStartTimerClick(getAdapterPosition());
        }

    }

    /*
     * Interface to detect recycler view click and pass position of clicked view holder to WOs activity.
     */
    public interface OnWorkoutListener {

        /**
         * Detect click on grid view and pass chosen view holder position to WOs activity.
         *
         * @param position position of view holder where *** is clicked
         */
        void onStartTimerClick(int position);

    }

}