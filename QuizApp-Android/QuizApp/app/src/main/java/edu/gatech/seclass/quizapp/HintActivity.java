package edu.gatech.seclass.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {
    private TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_page);
        hintText = (TextView) findViewById(R.id.hintTextField);

        // setting hint field
        Question question = (Question) getIntent().getSerializableExtra("question");
        System.out.println(question);
        System.out.println(question.getHint());
        hintText.setText(question.getHint());
    }

    @Override
    public void onBackPressed () {
        return;
    }

    public void goBackToQuestion(View view) {
        Question question = (Question) getIntent().getSerializableExtra("question");
        int questionType = question.getQuestionType();
        Intent intent = null;

        //1=MC, 2=SA, 3=Flashcard
        if (questionType == 1) {
            intent = new Intent(HintActivity.this, MultipleChoiceActivity.class);
            String selectedAnswer = getIntent().getExtras().getString("selectedAnswer");
            intent.putExtra("selectedAnswer", selectedAnswer);
        }

        if (questionType == 2) {
            intent = new Intent(HintActivity.this, ShortAnswerActivity.class);
            String selectedAnswer = getIntent().getExtras().getString("selectedAnswer");
            intent.putExtra("selectedAnswer", selectedAnswer);
        }

        if (questionType == 3) {
            intent = new Intent(HintActivity.this, FlashCardFrontActivity.class);
            intent.putExtra("fromHint", true);
        }

        intent.putExtra("question", question);
        startActivity(intent);
    }
}
