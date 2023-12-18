package edu.gatech.seclass.quizapp;

import java.sql.Timestamp;

public class UserAnswer {
    public String answerText;
    public String userID;
    public int quizID;
    public String questionID;
    public Timestamp timestamp;
    public float score;
    public String topic;

    public UserAnswer(Question question, Timestamp timestamp, String userID, String answerText, float score) {
        this.answerText = answerText;
        this.userID = userID;
        this.quizID = question.getQuizID();
        this.questionID = question.getQuestionID();
        this.timestamp = timestamp;
        this.score = score;
        this.topic = question.getTopic();
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getUserID() {
        return userID;
    }

    public int getQuizID() {
        return quizID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public float getScore() {
        return score;
    }

    public String getTopic() {
        return topic;
    }

    //for testing
    public String toString() {
        String answerText = "answerText: " + getAnswerText() + "\n";
        String userID = "userID: " + getUserID() + "\n";
        String questionID = "questionID: " + getQuestionID() + "\n";
        String timeStamp = "timeStamp: " + getTimestamp().toString() + "\n";
        String score = "score: " + getScore() + "\n";
        String topic = "topic: " + getTopic() + "\n";
        String stringCat = "quizID: " + quizID + "\n" + userID + questionID + timeStamp + answerText + score + topic;
        return stringCat;
    }
}
