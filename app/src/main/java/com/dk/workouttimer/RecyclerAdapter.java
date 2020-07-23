/*
 Author: Dominic Kennedy 160304253
 Purpose: Creates view holders and binds workout data to them
 */

package com.dk.workouttimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //private ArrayList<Exercise> mExerciseList;
    private ArrayList<Workout> mWorkoutArrayList;
    private LayoutInflater layoutInflater;
    private View.OnClickListener mOnItemClickListener;

    RecyclerAdapter(Context context, ArrayList<Workout> mWorkoutArrayList) {
        this.mWorkoutArrayList = mWorkoutArrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.workout_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override // bind data to views
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
       // Exercise exercise = mExerciseList.get(position);
        //holder.exerciseTxt.setText(exercise.getName());

        Workout workout = mWorkoutArrayList.get(position);
        holder.workoutTitle.setText(workout.getTitle());
        holder.duration.setText(String.format(Locale.getDefault(),
                "Duration: %d", workout.getTotalDuration()));
        holder.numberExercises.setText(String.format(Locale.getDefault(),
                "No. of exercises: %d", workout.getNoExercises()));

    }

    @Override
    public int getItemCount() {
        return mWorkoutArrayList.size();
    }

    // set view listener
    void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutTitle;
        TextView duration;
        TextView numberExercises;
        //ImageView exerciseIcon;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutTitle = itemView.findViewById(R.id.workout_txt);
            duration = itemView.findViewById(R.id.workout_duration_txt);
            numberExercises = itemView.findViewById(R.id.number_exercises_txt);


            //exerciseIcon = itemView.findViewById(R.id.workout_img);

            // for recycler onItemClick
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

}