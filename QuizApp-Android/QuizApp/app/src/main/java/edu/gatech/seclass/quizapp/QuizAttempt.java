package edu.gatech.seclass.quizapp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class QuizAttempt {
    public String userID;
    public int quizAttemptID;
    public int quizID;
    public Timestamp timestamp;
    public List<UserAnswer> answers;
    public int score;
    public List<Question> toggledQuestions;

    public QuizAttempt(String userID, int quizAttemptID, int quizID) {
        this.userID = userID;
        this.quizAttemptID = quizAttemptID;
        this.quizID = quizID;
        this.answers = new ArrayList<UserAnswer>();
        this.toggledQuestions = new ArrayList<Question>();
    }

    public void submitQuizAttempt() {
        return;
    }

    public int calculateScore() {
        for (UserAnswer ans : answers) {
            if (ans.score == 1) {
                this.score++;
            }
        }
        return score;
    }

    public List<UserAnswer> getUserAnswers() {
        return answers;
    }

    public void addUserAnswer(UserAnswer userAns) {
        answers.add(userAns);
    }

    // for testing
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            toString.append("*****Question# ").append(i).append("*****\n");
            toString.append(answers.get(i).toString());
        }

        return toString.toString();
    }

    public void addToggledQuestions(Question question) {
        toggledQuestions.add(question);
    }

    public List<Question> getToggledQuestions() {
        return toggledQuestions;
    }
}
