package edu.gatech.seclass.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class FlashCardFrontActivity2 extends AppCompatActivity implements Serializable {
    private TextView textView;
    private Button flipButton;
    private Button helpButton;
    private String frontText;
    private String backText;
    private String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(currentUser);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashcard_front);
        textView = (TextView) findViewById(R.id.frontText);
        helpButton = (Button) findViewById(R.id.helpButtonSA);

        if (getIntent().getExtras() == null) {
            QuizAppQuestion question = (QuizAppQuestion) (currentUser.currentQuizAppQuiz.getCurrentQuestion());
            currentUser.currentQuizAppQuiz.incrementCurrentIndex();
            questionId = question.id;
            frontText = question.question;
            backText = question.answer;
        } else if (getIntent().getExtras().getBoolean("fromBack")) {
            frontText = getIntent().getExtras().getString("front");
            backText = getIntent().getExtras().getString("back");
        }
        textView.setText(frontText);
        flipButton = (Button) findViewById(R.id.flipButton);
        flipButton.setOnClickListener(view -> goToBack());
        helpButton.setOnClickListener(this::goToHelp);
    }

    // go to back of flashcard
    public void goToBack() {
        Intent intent = new Intent(FlashCardFrontActivity2.this, FlashCardBackActivity2.class);

        intent.putExtra("front", frontText);
        intent.putExtra("back", backText);
        intent.putExtra("id", questionId);

        startActivity(intent);
    }

    public void goToHelp(View view) {
        Intent intent = new Intent(FlashCardFrontActivity2.this, HintActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}
