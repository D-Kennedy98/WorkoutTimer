/*
 Author: Dominic Kennedy 160304253
 Purpose: Creates view holders and binds workout data to them
 */

package com.dk.workouttimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Workout> mWorkoutList;
    private LayoutInflater layoutInflater;
    private View.OnClickListener mOnItemClickListener;

    RecyclerAdapter(Context context, ArrayList<Workout> workoutList) {
        this.mWorkoutList = workoutList;
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
        Workout workout = mWorkoutList.get(position);
        holder.exerciseTxt.setText(workout.getExercise());
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    // set view listener
    void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTxt;
       // ImageView exerciseIcon;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTxt = itemView.findViewById(R.id.workout_txt);
           // exerciseIcon = itemView.findViewById(R.id.workout_img);

            // for recycler onItemClick
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

}