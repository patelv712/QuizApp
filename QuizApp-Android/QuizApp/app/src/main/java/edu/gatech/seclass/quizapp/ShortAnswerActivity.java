package edu.gatech.seclass.quizapp;

import java.sql.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
/* Creating global User. This is probably not the right way
to do this in an Android app, but will work for now.
To use currentUser in another activity, add:
*/
import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.Locale;

public class ShortAnswerActivity extends AppCompatActivity {
    private EditText inputText; // will be the user's input
    private TextView textView; // will display whether the user is correct or not
    private TextView questionTextView; // will display the question
    private ShortAnswerQuestion shortAnswerQuestion; // the question from the demo question bank
    private Button nextButton;
    private Button submitButton;
    private Button helpButton;
    private HashMap<Integer, ArrayList<String>> answerBank = new HashMap<Integer, ArrayList<String>>();
    private Boolean foundAnswer = false;
    private SwitchCompat toggleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);
        submitButton = (Button) findViewById(R.id.submitButtonSA);
        helpButton = (Button) findViewById(R.id.helpButtonSA);
        inputText = (EditText) findViewById(R.id.studentInput);
        textView = (TextView) findViewById(R.id.displayAnswer);
        if (getIntent().getExtras() != null) {
            shortAnswerQuestion = (ShortAnswerQuestion) getIntent().getSerializableExtra("question");
            inputText.setText((String) (getIntent().getExtras().getString("selectedAnswer")));
        } else {
            shortAnswerQuestion = (ShortAnswerQuestion) currentUser.currentQuiz.getCurrentQuestion();
        }

        submitButton = (Button) findViewById(R.id.submitButtonSA);
        helpButton = (Button) findViewById(R.id.helpButtonSA);
        inputText = (EditText) findViewById(R.id.studentInput);
        textView = (TextView) findViewById(R.id.displayAnswer);
        toggleSwitch = findViewById(R.id.shortanswerswitch);

        // casts the current question in the quiz to object type ShortAnswerQuestion,
        // bc if this onCreate method is activated then that's the type of question
        // we're on
        questionTextView = (TextView) findViewById(R.id.displayQuestion);
        questionTextView.setText(shortAnswerQuestion.questionText);

        // Sets the header to display the text for the question that was defined
        // in MainActivity
        //listeners
        submitButton.setOnClickListener(view -> {
            if (inputText.getText().toString().trim().length() == 0) {
                textView.setText(new String("A blank answer can't be submitted"));
            } else {
                submitFunctionality(view);
            }
        });
        helpButton.setOnClickListener(this::goToHelp);
    }

    public void goToNextScreen(View view) {
        int nextQ = currentUser.currentQuiz.peekNextQuestionType();
        Intent intent = new Intent(ShortAnswerActivity.this, ResultsActivity.class);

        if (nextQ == -1) { // go to Results activity once quiz is finished
            intent = new Intent(ShortAnswerActivity.this, ResultsActivity.class);
        } else if (nextQ == 1) { //go to multiple choice
            intent = new Intent(ShortAnswerActivity.this, MultipleChoiceActivity.class);
        } else if (nextQ == 2) { //go to short answer
            intent = new Intent(ShortAnswerActivity.this, ShortAnswerActivity.class);
        } else if (nextQ == 3) { //go to flashcard
            intent = new Intent(ShortAnswerActivity.this, FlashCardFrontActivity.class);
        }
        startActivity(intent);
    }

    public void submitFunctionality(View view) {
        String textViewStr;
        float correct = shortAnswerQuestion.gradeAnswer(inputText.getText().toString());
        if (correct == 1) {
            textViewStr = "" + inputText.getText() + " is correct!";
        } else {
            textViewStr = "" + inputText.getText() + " is incorrect. The correct answer is "
                    + shortAnswerQuestion.getCorrectAnswer();
        }

        // creating userAnswer object and appending it to currentUser.quizAttempt
        String answerText = inputText.getText().toString();

        // dummy for now
        Timestamp dummy = new Timestamp(2000);
        UserAnswer userAnswer = new UserAnswer(shortAnswerQuestion, dummy, currentUser.userID, answerText, correct);
        currentUser.currentQuizAttempt.addUserAnswer(userAnswer);

        textView.setText(textViewStr);
        if (toggleSwitch.isChecked()) {
            currentUser.currentQuizAttempt.addToggledQuestions(shortAnswerQuestion);
        }

        goToNextScreen(view);
    }

    public void goToHelp(View view) {
        Intent intent = new Intent(ShortAnswerActivity.this, HintActivity.class);
        intent.putExtra("question", shortAnswerQuestion);
        String text = inputText.getText().toString();
        intent.putExtra("selectedAnswer", text);

        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}