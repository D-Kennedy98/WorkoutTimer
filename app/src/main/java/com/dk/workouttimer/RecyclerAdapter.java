package com.dk.workouttimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Workout> mWorkoutList;
    private LayoutInflater layoutInflater;
    private View.OnClickListener mOnItemClickListener;

    public RecyclerAdapter(Context context, ArrayList<Workout> workoutList) {
        this.mWorkoutList = workoutList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.workout_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Workout workout = mWorkoutList.get(position);
        holder.exerciseTxt.setText(workout.getExercise());

    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    // set view listener
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTxt = itemView.findViewById(R.id.exerciseTxt);
            // for recycler onItemClick
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }



    }
}