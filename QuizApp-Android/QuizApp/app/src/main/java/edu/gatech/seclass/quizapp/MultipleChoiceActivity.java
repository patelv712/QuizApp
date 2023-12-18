package edu.gatech.seclass.quizapp;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Timestamp;
import java.text.BreakIterator;

public class MultipleChoiceActivity extends AppCompatActivity {
    private Button submitButton;
    private Button helpButton;
    private TextView textView;
    private MultipleChoiceQuestion multipleChoiceQuestion;
    private RadioGroup radioGroup;
    private List<RadioButton> answerButtons = new ArrayList<>();
    private Switch toggleSwitch;

    public void onCreate(Bundle savedInstanceState) {
        System.out.println(currentUser);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplechoice_question);
        if (getIntent().getExtras() != null) {
            multipleChoiceQuestion = (MultipleChoiceQuestion) getIntent().getSerializableExtra("question");
        } else {
            multipleChoiceQuestion = (MultipleChoiceQuestion) currentUser.currentQuiz.getCurrentQuestion();
        }

        // initializing
        textView = findViewById(R.id.questionText);
        answerButtons.add(findViewById(R.id.ans1radioButton));
        answerButtons.add(findViewById(R.id.ans2radioButton));
        answerButtons.add(findViewById(R.id.ans3radioButton));
        answerButtons.add(findViewById(R.id.correctAnsradioButton));
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitButtonSA);
        helpButton = findViewById(R.id.helpButtonSA3);
        textView.setText(multipleChoiceQuestion.questionText);
        toggleSwitch = findViewById(R.id.mcqswitch);

        // setting answer choices
        List<String> answerChoicesText = new ArrayList<>(multipleChoiceQuestion.getIncorrectChoices());
        answerChoicesText.add(multipleChoiceQuestion.getCorrectAnswer());
        Collections.shuffle(answerChoicesText);

        // TODO make this more generalized for any number of answer buttons
        for (int i = 0; i < answerButtons.size(); i++) {
            answerButtons.get(i).setText(answerChoicesText.get(i));
        }


        //restore previously selected button
        if (getIntent().getExtras() != null) {
            for (Button button : answerButtons) {
                String selectedAnswer = getIntent().getExtras().getString("selectedAnswer");
                if (button.getText().equals(selectedAnswer)) {
                    System.out.println("true");
                    button.performClick();

                }
            }
        }

        // listeners
        submitButton.setOnClickListener(view -> submitFunctionality());
        helpButton.setOnClickListener(view -> goToHelp());
    }

    public void goToNextScreen() {
        int nextQ = currentUser.currentQuiz.peekNextQuestionType();
        Intent intent = new Intent(MultipleChoiceActivity.this, MainActivity.class);

        if (nextQ == -1) {
            // go to Results activity once quiz is finished
            intent = new Intent(MultipleChoiceActivity.this, ResultsActivity.class);
        } else if (nextQ == 1) {
            intent = new Intent(MultipleChoiceActivity.this, MultipleChoiceActivity.class);
        } else if (nextQ == 2) {
            intent = new Intent(MultipleChoiceActivity.this, ShortAnswerActivity.class);
        } else if (nextQ == 3) {
            // go to flashcard
            intent = new Intent(MultipleChoiceActivity.this, FlashCardFrontActivity.class);
        }
        startActivity(intent);
    }


    public void submitFunctionality() {
        //implement submit functionality here
        int selectedID = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = (RadioButton) findViewById(selectedID);

        if (selectedButton != null) {
            String answerText = selectedButton.getText().toString();
            float correct = multipleChoiceQuestion.gradeAnswer(answerText);
            //creating userAnswer object and appending it to currentUser.quizAttempt
            Timestamp dummy = new Timestamp(2000);
            UserAnswer userAnswer = new UserAnswer(multipleChoiceQuestion, dummy, currentUser.userID, answerText, correct);
            currentUser.currentQuizAttempt.addUserAnswer(userAnswer);
            if (toggleSwitch.isChecked()) {
                currentUser.currentQuizAttempt.addToggledQuestions(multipleChoiceQuestion);
            }
            goToNextScreen();
        }
    }

    public void goToHelp() {
        Intent intent = new Intent(MultipleChoiceActivity.this, HintActivity.class);
        intent.putExtra("question", multipleChoiceQuestion);
        int selectedID = radioGroup.getCheckedRadioButtonId();

        RadioButton selectedButton = (RadioButton) findViewById(selectedID);
        if (selectedButton != null) {
            intent.putExtra("selectedAnswer", selectedButton.getText().toString());
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}
