/*
 * Author: Dominic Kennedy
 * Purpose: Allows workout objects to be displayed as a list.
 */

package com.dk.workouttimer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.workouttimer.R;
import com.dk.workouttimer.models.Workout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    /**
     * Data set binded to adapter view holders.
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
     * @return view holder representing contents of workout item in arrayList
     */
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.workout_grid_layout, parent, false);
        return new ViewHolder(view, mWorkoutListener);
    }

    /**
     * Binds workout data from arrayList to view holders.
     *
     * @param holder view holder
     * @param position index of array list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Workout workout = mWorkoutArrayList.get(position);
        holder.workoutTitle.setText(workout.getTitle());
        holder.duration.setText(String.format(Locale.getDefault(),
                "Duration: %d", workout.getTotalDuration()));
        holder.numberExercises.setText(String.format(Locale.getDefault(),
                "No. of exercises: %d", workout.getNoExercises()));
    }

    /**
     * Get number of items in adaptor arrayList.
     *
     * @return number of items adaptor arrayList.
     */
    @Override
    public int getItemCount() {
        return mWorkoutArrayList.size();
    }

    /**
     * Represents contents of item in data set. Holders are recycled as user scrolls.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView workoutTitle, duration, numberExercises;
        OnWorkoutListener workoutListener;

        ViewHolder(@NonNull View itemView, OnWorkoutListener workoutListener) {
            super(itemView);
            workoutTitle = itemView.findViewById(R.id.workout_txt);
            duration = itemView.findViewById(R.id.workout_duration_txt);
            numberExercises = itemView.findViewById(R.id.number_exercises_txt);
            this.workoutListener = workoutListener;

            // this refers to onClickListener interface
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            workoutListener.onWorkoutClick(getAdapterPosition());
        }
    }

    /*
     * Interface to detect recycler view click and pass position of clicked item
     * to WO activity.
     */
    public interface OnWorkoutListener{
        void onWorkoutClick(int position);
    }

}