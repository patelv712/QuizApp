package edu.gatech.seclass.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class FlashCardFrontActivity extends AppCompatActivity implements Serializable {
    private TextView textView;
    private Button flipButton;
    private Button helpButton;
    private String frontText;
    private String backText;

    private int recordedAnswer;
    private boolean isAnswerRecorded;
    private FlashCardQuestion flashCardQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(currentUser);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashcard_front);
        textView = (TextView) findViewById(R.id.frontText);
        helpButton = (Button) findViewById(R.id.helpButtonSA);

        QuizAppQuestion question = (QuizAppQuestion) (currentUser.currentQuizAppQuiz.getCurrentQuestion());
        frontText = question.question;

        // if coming from hint

        if (getIntent().getExtras() == null) {
            flashCardQuestion = (FlashCardQuestion) (currentUser.currentQuiz.getCurrentQuestion());
            frontText = flashCardQuestion.questionText;
            backText = flashCardQuestion.getBackOfCard();
        } else {
            if (getIntent().getExtras().getBoolean("fromHint")) {
                flashCardQuestion = (FlashCardQuestion) getIntent().getSerializableExtra("question");
                frontText = flashCardQuestion.questionText;
                backText = flashCardQuestion.getBackOfCard();
            }
            if (getIntent().getExtras().getBoolean("fromBack")) {
                flashCardQuestion = (FlashCardQuestion) getIntent().getSerializableExtra("flashCard");
                isAnswerRecorded = getIntent().getExtras().getBoolean("isAnswered");
                recordedAnswer = getIntent().getExtras().getInt("recordedAnswer");
                frontText = getIntent().getExtras().getString("front");
                backText = getIntent().getExtras().getString("back");
            }
        }

        textView.setText(frontText);
        flipButton = (Button) findViewById(R.id.flipButton);
        flipButton.setOnClickListener(view -> goToBack());
        helpButton.setOnClickListener(this::goToHelp);
    }

    // user goes to next question in the list
    // if there's no question, it goes to main activity
    public void goToNextScreen() {
        int nextQ = currentUser.currentQuiz.peekNextQuestionType();
        Intent intent = new Intent(FlashCardFrontActivity.this, ResultsActivity.class);

        if (nextQ == -1) {
            // go to main activity
            intent = new Intent(FlashCardFrontActivity.this, ResultsActivity.class);
        } else if (nextQ == 1) {
            intent = new Intent(FlashCardFrontActivity.this, MultipleChoiceActivity.class);
        } else if (nextQ == 2) {
            intent = new Intent(FlashCardFrontActivity.this, ShortAnswerActivity.class);
        } else if (nextQ == 3) {
            // go to flashcard
            intent = new Intent(FlashCardFrontActivity.this, FlashCardFrontActivity.class);
        }
        startActivity(intent);
    }

    // go to back of flashcard
    public void goToBack() {
        Intent intent = new Intent(FlashCardFrontActivity.this, FlashCardBackActivity.class);

        intent.putExtra("front", frontText);
        intent.putExtra("back", backText);
        intent.putExtra("flashCard", flashCardQuestion);
        intent.putExtra("recordedAnswer", recordedAnswer);
        intent.putExtra("isAnswered", isAnswerRecorded);

        startActivity(intent);
    }

    public void goToHelp(View view) {
        Intent intent = new Intent(FlashCardFrontActivity.this, HintActivity.class);
        intent.putExtra("question", flashCardQuestion);

        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}
