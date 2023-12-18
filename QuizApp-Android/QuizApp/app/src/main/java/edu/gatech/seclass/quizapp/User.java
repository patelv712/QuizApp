package edu.gatech.seclass.quizapp;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class User {
    public String userID;
    public String password;
    public String name;
    public QuizAttempt[] pastQuizzes;
    public QuizAttempt currentQuizAttempt;
    public Quiz currentQuiz;
    public QuizAppQuiz currentQuizAppQuiz;
    public Bitmap pfp;

    public boolean isCredentials() {
        return credentials;
    }

    public void setCredentials(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public boolean credentials;

    public void displayUserHistory() {
        return;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
