package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnalyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
    }

    @SuppressLint("SetTextI18n")
    public void launchMain(View view) {
        Intent intent = new Intent(AnalyticsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}