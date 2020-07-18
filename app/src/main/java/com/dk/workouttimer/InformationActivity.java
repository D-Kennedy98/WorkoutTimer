/*
 Author: Dominic Kennedy 160304243
 Purpose: Implements information screen which provides users with info about the application
 */

package com.dk.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // init back button view and set on click listener
        ImageView mBackBtn = findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        startActivity(intent);
    }

}
