package edu.gatech.seclass.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BrowseQuizzes extends MainActivity {
    private Button demo_quiz;
    private Button quiz_one;
    private Button quiz_two;
    private Button home;
    private Button recommended_quiz;
    private ListView listView;
    private SearchView searchView;

    public static ArrayList<String> quizNamesList = new ArrayList<String>();

    // create objects to interact with widgets - tied by widget ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_quizzes);

        demo_quiz = (Button) findViewById(R.id.browse_quiz_button_demo);
        quiz_one = (Button) findViewById(R.id.browse_quiz_button_1);
        quiz_two = (Button) findViewById(R.id.browse_quiz_button_2);
        recommended_quiz = (Button) findViewById(R.id.browse_quiz_button_recommended);
        home = (Button) findViewById(R.id.browse_screen_home_button);
        searchView = (SearchView) findViewById(R.id.search_bar);

        setUpData();
        setUpList();
        setUpSearchBar();
    }

    private void setUpData() {
        if (quizNamesList.size() == 0) {
            quizNamesList.add("quiz 1 review");
            quizNamesList.add("quiz 2");
            quizNamesList.add("hard quiz");
            quizNamesList.add("fourier analysis");
            quizNamesList.add("fourier transform");
            quizNamesList.add("fourier series");
            quizNamesList.add("Signal Analysis");
            quizNamesList.add("for exam 1");
            quizNamesList.add("frequency response");
            quizNamesList.add("convolutions");
            quizNamesList.add("Final Exam Review");

        }
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.search_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_search_bar, quizNamesList);
        listView.setAdapter(adapter);
    }

    public void setUpSearchBar() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.equals("")) {
                    listView.setVisibility(View.VISIBLE);
                    ArrayList<String> filteredQuizNames = new ArrayList<String>();
                    for (String quizName : quizNamesList) {
                        if (quizName.toLowerCase().contains(s.toLowerCase())) {
                            filteredQuizNames.add(quizName);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_view_search_bar, filteredQuizNames);
                    listView.setAdapter(adapter);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }

    // switches to demo screen
    public void loadQuiz(View view) {
        if (view.getId() == quiz_one.getId()) {
            launchDemoQuiz(view);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Error: Quiz not created yet.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    // in the on response method, get response, parse it, pass the list of questions into launchDemoQuiz, which will take it to new screen, run it on ui thread
    public void launchDemoQuiz(View view) {
        Quiz demoQuiz;
        MultipleChoiceQuestion demoQ1;
        FlashCardQuestion demoQ2;
        ShortAnswerQuestion demoQ3;
        MultipleChoiceQuestion demoQ4;

        // Generate Questions
        demoQ1 = new MultipleChoiceQuestion("demoQ1", 1, "The period T of a periodic signal x(t) is:", "");
        List<String> demoQ1list = new ArrayList<>();
        demoQ1list.add("The height of the largest peak in signal graph");
        demoQ1list.add("The number of peaks in a given");
        demoQ1list.add("incorrect answer 3");
        demoQ1.setIncorrectChoices(demoQ1list);
        demoQ1.setCorrectAnswer("The values of the signal repeat every T seconds");
        demoQ1.setHint("Another way to describe a period is any one full pattern in a cycle.");

        demoQ2 = new FlashCardQuestion("demoQ2", 1, "Amplitude modulation", "hint");
        demoQ2.setBackOfCard("process of multiplying a high frequency sinusoidal signal by a low frequency signal");
        demoQ2.setHint("Used chiefly as a means of radio broadcasting.");

        demoQ3 = new ShortAnswerQuestion("demoQ3", 1, "Identify the following signal curve?", "hint");
        demoQ3.setCorrectAnswer("Sine Graph");
        demoQ3.setHint("Think about asymptotes and where the graph crosses the y-axis.");

        demoQ4 = new MultipleChoiceQuestion("demoQ4", 1, "What is a spectrum?", "hint");
        List<String> demoQ4list = new ArrayList<>();
        demoQ4list.add("The derivative of the angle function");
        demoQ4list.add("Another name for a linear FM signal");
        demoQ4list.add("incorrect answer 3");
        demoQ4.setIncorrectChoices(demoQ4list);
        demoQ4.setCorrectAnswer("a graphical presentation of the complex amplitude for each frequency component in the signal");
        demoQ4.setHint("A spectrum has something to do with the amplitude of a signal.");

        // Init a new quiz
        demoQuiz = new Quiz();
        currentUser.currentQuiz = demoQuiz;

        // init quizattempt object
        int quizAttemptID = 1; //todo: increment based on number of quizzes
        currentUser.currentQuizAttempt = new QuizAttempt(currentUser.userID, quizAttemptID, currentUser.currentQuiz.quizID);

        // Add all demo questions to the currentUser.currentQuiz object
        // method 'addQuestionToQuiz' returns the size of the list when called
        currentUser.currentQuiz.addQuestionToQuiz(demoQ1);
        currentUser.currentQuiz.addQuestionToQuiz(demoQ2);
        currentUser.currentQuiz.addQuestionToQuiz(demoQ3);
        currentUser.currentQuiz.addQuestionToQuiz(demoQ4);

        // Get type of first question to go to the appropriate screen
        int nextQ = currentUser.currentQuiz.peekNextQuestionType();

        Intent intent;
        if (nextQ == -1) {
            //go to Results activity once quiz is finished
            intent = new Intent(BrowseQuizzes.this, ResultsActivity.class);
        } else if (nextQ == 1) {
            intent = new Intent(BrowseQuizzes.this, MultipleChoiceActivity.class);
        } else if (nextQ == 2) {
            intent = new Intent(BrowseQuizzes.this, ShortAnswerActivity.class);
        } else if (nextQ == 3) {
            //go to flashcard
            intent = new Intent(BrowseQuizzes.this, FlashCardFrontActivity.class);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Error launching quiz. Unexpected next question type.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        startActivity(intent);
    }

    // Adding home button functionality
    public void goToHome(View view) {
        if (view.getId() == home.getId()) {
            Intent intent = new Intent(BrowseQuizzes.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed () {
        return;
    }

    //sends request for and launches random quiz
    public void sendReqAndLaunchRandomQuiz(View view) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://" + Login.ipa + ":3000/readrandom", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Quiz quiz = JsonUtil.parseQuiz(response);
                System.out.println(quiz + " XXXXXX ");
                launchParsedQuiz(quiz);
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

    //launch recommended quiz from db
    public void sendReqAndLaunchRecommendedQuiz(View view) {
        // put in IP address of your laptop here
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://" + Login.ipa + ":3000/readrecommended", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Quiz quiz = JsonUtil.parseQuiz(response);
                launchParsedQuiz(quiz);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: " + error);

            }
        });
        SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }


    public void launchParsedQuiz(Quiz quiz) {
        currentUser.currentQuiz = quiz;
        int quizAttemptID = 1; //todo: increment based on number of quizzes
        QuizAttempt quizAttempt = new QuizAttempt(currentUser.userID, quizAttemptID, currentUser.currentQuiz.quizID);
        currentUser.currentQuizAttempt = quizAttempt;

        int nextQ = currentUser.currentQuiz.peekNextQuestionType();
        Intent intent = new Intent(BrowseQuizzes.this, MainActivity.class);

        if (nextQ == -1) {
            //go to Results activity once quiz is finished
            intent = new Intent(BrowseQuizzes.this, ResultsActivity.class);
        } else if (nextQ == 1) {
            intent = new Intent(BrowseQuizzes.this, MultipleChoiceActivity.class);
        } else if (nextQ == 2) {
            intent = new Intent(BrowseQuizzes.this, ShortAnswerActivity.class);
        } else if (nextQ == 3) {
            //go to flashcard
            intent = new Intent(BrowseQuizzes.this, FlashCardFrontActivity.class);
        }
        startActivity(intent);
    }

    /**
     * Sends a request for quizzes from a certain chapter and section.
     *
     * @param view  current view
     */

    public void requestQuizAppQuiz(View view) {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest("http://" + Login.ipa + ":3000/readChapterQuestions/f/1/10",
                        new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                QuizAppQuiz quiz = JsonUtil.parseQuizAppQuiz(response);
                System.out.println("Quiz Parsed");
                launchQuizAppQuiz(quiz);
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
     * Launches the flashcard screen to begin the quiz.
     *
     * @param quiz current view
     */

    public void launchQuizAppQuiz(QuizAppQuiz quiz) {
        currentUser.currentQuizAppQuiz = quiz;
        Intent intent = new Intent(BrowseQuizzes.this, FlashCardFrontActivity2.class);
        startActivity(intent);
    }
}
