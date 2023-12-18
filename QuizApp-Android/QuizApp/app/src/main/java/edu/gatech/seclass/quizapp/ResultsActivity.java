package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {
    private TextView overallScore;
    private Button saveResultsButton;
    private TextView toggleText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (currentUser == null) {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
        }

        overallScore = (TextView) findViewById(R.id.overallScoreTextView);
        int quizLength = currentUser.currentQuiz.numberOfQuestions();
        int numCorrectAnswers = currentUser.currentQuizAttempt.calculateScore();

        String toggleReview = "";
        toggleText = (TextView) findViewById(R.id.saveToggleText);
        for (int i = 0; i < currentUser.currentQuizAttempt.getToggledQuestions().size(); i++) {
            String reviewQuestions = currentUser.currentQuizAttempt.toggledQuestions.get(i).questionText;
            toggleReview += reviewQuestions + " ";
        }
        String scoreText = "Great job! You answered " + numCorrectAnswers + "/" + quizLength + " questions correctly";
        overallScore.setText(scoreText);
        toggleText.setText("These are your flagged questions: " + toggleReview);
        saveResultsButton = (Button) findViewById(R.id.saveResultsButton);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // todo: make it send quizattempt object to database
    public void saveResults(View view) {
        // can't save results more than once
        saveResultsButton.setEnabled(false);
        String url = "http://" + Login.ipa + ":3000/recordAnswers";
        final JSONObject reqBody = JsonUtil.convertQuizAttempt(currentUser.currentQuizAttempt);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return reqBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(stringRequest);
    }

    @Override
    public void onBackPressed () {
        return;
    }

}
