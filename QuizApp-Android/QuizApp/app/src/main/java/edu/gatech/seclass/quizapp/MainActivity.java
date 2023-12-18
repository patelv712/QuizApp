package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /* Creating global User. This is probably not the right way
     to do this in an Android app, but will work for now.
     To use currentUser in another activity, add:
     import static edu.gatech.seclass.quizapp.MainActivity.currentUser;
     */
    protected static User currentUser;
    private TextView outputText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.outputText);

        // Redirect to the login page if current user is null
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(intent);
        } else {
            outputText.setText("\n\nHello " + currentUser.name + "!");
        }
    }

    // How to Switch Screens and Launch New Ones
    @SuppressLint("SetTextI18n")
    public void launchBrowseQuizzes(View view) {
        outputText.setText("You clicked Browse Quizzes Screen. Taking you to browse quizzes screen.");
        Intent intent = new Intent(MainActivity.this, BrowseQuizzes.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void launchAnalytics(View view) {
        outputText.setText("You clicked Analytics. Taking you to analytics screen.");
        Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void launchLoginScreen(View view) {
        outputText.setText("You clicked Sign Out. Signing you out");
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    @SuppressLint("SetTextI18n")
    public void launchHomeScreen(View view) {
        outputText.setText("You clicked Home. Taking you to Home screen");
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}
