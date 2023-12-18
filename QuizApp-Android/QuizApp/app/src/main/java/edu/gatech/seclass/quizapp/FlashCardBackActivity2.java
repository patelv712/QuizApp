package edu.gatech.seclass.quizapp;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;

public class FlashCardBackActivity2 extends AppCompatActivity implements Serializable {
    private TextView textView;

    private Button submitButton;
    private Button flipButton;
    private String frontText;
    private String backText;
    private String questionId;
    private int correct;
    private Switch toggleSwitch;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private ArrayList<Integer> difficultyLevel = new ArrayList<>();
    private int currentAnswer;

    private boolean isAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        System.out.println(currentUser);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_back);

        textView = (TextView) findViewById(R.id.backText);
        frontText = getIntent().getExtras().getString("front");
        backText = getIntent().getExtras().getString("back");
        questionId = getIntent().getExtras().getString("id");

        textView.setText(backText);
        initializeButtons();
    }

    private void initializeButtons() {
        currentAnswer = -1;

        easyButton = (Button) findViewById(R.id.easyButton);
        mediumButton = (Button) findViewById(R.id.mediumButton);
        hardButton = (Button) findViewById(R.id.hardButton);

        easyButton.setOnClickListener(view -> {
            currentAnswer = 1;
            System.out.println("currentAnswer is " + currentAnswer);
        });

        mediumButton.setOnClickListener(view -> {
            currentAnswer = 3;
            System.out.println("currentAnswer is " + currentAnswer);
        });

        hardButton.setOnClickListener(view -> {
            currentAnswer = 5;
            System.out.println("currentAnswer is " + currentAnswer);
        });

        submitButton = (Button) findViewById(R.id.submitButtonFB);
        submitButton.setOnClickListener(view -> {
            if (currentAnswer > 0){
                System.out.println("correct: "+correct);
                System.out.println("Submitted answer is " + currentAnswer);
                difficultyLevel.add(currentAnswer);
                findQuestionAttempt(questionId, currentAnswer);
                goToNextScreen();
            }
        });

        flipButton = (Button) findViewById(R.id.flipButton);
        flipButton.setOnClickListener(view -> goToFront());
        toggleSwitch = findViewById(R.id.flashcardbackswitch);
    }

    @Override
    public void onBackPressed () {
        return;
    }

    /**
     * Method for what to do after flashcard is submitted (go to results if over or
     * go to next flashcard)
     */

    public void goToNextScreen() {
        QuizAppQuestion question = currentUser.currentQuizAppQuiz.getCurrentQuestion();
        Intent intent;
        if (question == null) {
            intent = new Intent(FlashCardBackActivity2.this, BrowseQuizzes.class);
        } else {
            intent = new Intent(FlashCardBackActivity2.this, FlashCardFrontActivity2.class);
        }
        startActivity(intent);
    }

    public void goToFront() {
        Intent intent = new Intent(FlashCardBackActivity2.this, FlashCardFrontActivity2.class);

        intent.putExtra("front", frontText);
        intent.putExtra("back", backText);
        intent.putExtra("id", questionId);
        intent.putExtra("fromBack", true);
        startActivity(intent);
    }

    /**
     * Method to find question attempt corresponding to the question and user ID.
     */

    public void findQuestionAttempt(String questionId, int difficulty) {
        int quality = 5 - difficulty;
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest("http://" + Login.ipa + ":3000/findQuestionAttempt/" + currentUser.userID + "/" + questionId,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    System.out.println(response.getJSONObject(0).getString("_id"));
                                    updateQuestionAttempt(response.getJSONObject(0).getString("_id"), quality);
                                } catch (JSONException e) {
                                    System.out.println("Error retrieving JSON Object.");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error1: " + error);

                    }
                });
        SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }

    /**
     * Update question attempt based on difficulty inputted from user.
     */

    public void updateQuestionAttempt(String questionAttemptId, int quality) {
        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest("http://" + Login.ipa + ":3000/updateQuestionAttempt/"
                        + questionAttemptId +"/" + quality, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error1: " + error);

                    }
                });
        SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }
}


