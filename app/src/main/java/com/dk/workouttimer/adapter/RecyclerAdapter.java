/*
 Author: Dominic Kennedy
 Purpose: Creates view holders and binds workout data to them.
 */

package com.dk.workouttimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dk.workouttimer.R;
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
     * @param context current state of app
     * @param workoutArrayList data set containing workout data to be bound to view holders
     * @param workoutListener allows any object that implements OnWorkoutListener interface to be passed
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
     * @param parent view group that new view will be added to after binding to adapter
     * @param viewType view type of new view
     * @return view holder representing contents of workout item in the data set
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
     * @param holder view holder
     * @param position position of view holder
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Workout workout = mWorkoutArrayList.get(position);
        holder.workoutTitle.setText(workout.getTitle());
        holder.duration.setText(String.format(Locale.getDefault(),
                "Duration: %s", convertTime(workout.getTotalDuration())));
        holder.numberExercises.setText(String.format(Locale.getDefault(),
                "No. of exercises: %d", workout.getNoExercises()));
    }

    /**
     * Get number of items in adaptor data set.
     *
     * @return number of items adaptor data set
     */
    @Override
    public int getItemCount() {
        return mWorkoutArrayList.size();
    }

    /**
     * Convert time in seconds to format ss:mm.
     *
     * @param time time being converted
     * @return string of time in format ss:mm
     */
    private String convertTime(long time) {
        int mins = (int) time / 60;
        int secs = (int) time % 60;
        return  String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
    }

    /**
     * Represents contents of item in data set. Holders are recycled as user scrolls and
     * new items become visible.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button deleteBtn;
        TextView workoutTitle, duration, numberExercises;
        OnWorkoutListener workoutListener;

        ViewHolder(@NonNull View itemView, OnWorkoutListener workoutListener) {
            super(itemView);
            workoutTitle = itemView.findViewById(R.id.workout_txt);
            duration = itemView.findViewById(R.id.workout_duration_txt);
            numberExercises = itemView.findViewById(R.id.number_exercises_txt);
            this.workoutListener = workoutListener;

            deleteBtn = itemView.findViewById(R.id.delete_btn);

            // this refers to onClickListener interface
            workoutTitle.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        /**
         * Gets position of item in adapter when clicked.
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            // check which view is clicked
           if(deleteBtn.isPressed()) {
               workoutListener.onDeleteClick(getAdapterPosition());
           } else if (workoutTitle.isPressed()){
               workoutListener.onStartTimerClick(getAdapterPosition());
           }
        }
    }

    /*
     * Interface to detect recycler view click
     * and pass position of clicked item to WO activity.
     * TODO: Separate file?
     */
    public interface OnWorkoutListener {

        /**
         * Detect click on *** and pass chosen view holder position to WO activity.
         *
         * @param position position of view holder where *** is clicked
         */
        void onStartTimerClick(int position);

        /**
         * Detect click on delete button and pass chosen view holder position to WO activity.
         *
         * @param position position of view holder where delete button is clicked
         */
        void onDeleteClick(int position);
    }

}