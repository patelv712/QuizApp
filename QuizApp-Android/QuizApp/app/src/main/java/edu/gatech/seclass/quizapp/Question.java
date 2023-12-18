package edu.gatech.seclass.quizapp;


import java.io.Serializable;

public class Question implements Serializable {
    public String questionID;
    public int quizID;
    public String questionText;
    public String correctAnswer;
    public String topic; // instead of a list of topics

    //Use questionType to indicate what kind of question. 1=MC, 2=SA, 3=Flashcard
    public int questionType;
    public String hint;

    public Question(String questionID, int quizID, String questionText, String hint) {
        this.questionID = questionID;
        this.quizID = quizID; // this specifies what quiz the question belongs to, NOT the question
        // type
        this.questionText = questionText;
    }

    public float gradeAnswer(String userAnswer) {
        return 0;
    }

    public void setQuestionType(int type) {
        this.questionType = type;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQuizID() {
        return this.quizID;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionID() {
        return questionID;
    }

    public String getTopic() {
        return topic;
    }

    public int getQuestionType() {
        return questionType;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

