package edu.gatech.seclass.quizapp;

import java.util.List;
import java.util.Locale;
//    private List<String> incorrectChoices;

public class ShortAnswerQuestion extends Question {

    public ShortAnswerQuestion(String questionID, int quizID, String questionText, String hint) {
        super(questionID, quizID, questionText, hint); //constructed in Question class
        this.questionType = 2;
    }

    public float gradeAnswer(String userAns) {
        String ans = userAns.toLowerCase().trim();
        String correctAns = correctAnswer.toLowerCase().trim();
        return ans.equals(correctAns) ? 1 : 0;
    }

    public String toString() {
        String id = "qid: " + questionID + "\n";
        String quizid = "quizid: " + quizID + "\n";
        String qText = "questiontext: " + questionText + "\n";
        String answer = "answer: " + correctAnswer + "\n";
        String topi = "topic: " + topic + "\n";
        String qtype = "type: " + questionType + "\n";
        String toString = id + quizid + qText + answer + topi + qtype;
        return toString;
    }
}
