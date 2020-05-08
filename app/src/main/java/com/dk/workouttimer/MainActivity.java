package com.dk.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Workout> mWorkoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        // create workout data
        Workout pressUps = new Workout("Press Ups", 0);
        Workout sitUps = new Workout("Sit Ups", 0);
        Workout yoga = new Workout("Yoga", 0);
        Workout squats = new Workout("Squats", 0);
        Workout run = new Workout("Run", 0);
        Workout burpees = new Workout("Burpees", 0);

        // add workouts to list
        mWorkoutList.add(pressUps);
        mWorkoutList.add(sitUps);
        mWorkoutList.add(yoga);
        mWorkoutList.add(squats);
        mWorkoutList.add(run);
        mWorkoutList.add(burpees);


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mWorkoutList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(onItemClickListener);

    }


    // allows individual recycler view items to be retrieved
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            int pos = holder.getAdapterPosition();
            Workout workout = mWorkoutList.get(pos);
            launchWorkoutActivity(workout);

        }
    };



    private void launchWorkoutActivity(Workout workout) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("workout", workout);
        startActivity(intent);
    }

}
