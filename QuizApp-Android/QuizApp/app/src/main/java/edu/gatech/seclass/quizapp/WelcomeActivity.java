package edu.gatech.seclass.quizapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.gatech.seclass.quizapp.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private AppBarConfiguration appBarConfiguration;
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(WelcomeActivity.this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(WelcomeActivity.this, Login.class));
    }
}