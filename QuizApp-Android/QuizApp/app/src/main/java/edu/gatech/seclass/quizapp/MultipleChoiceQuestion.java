package edu.gatech.seclass.quizapp;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> incorrectChoices;

    public MultipleChoiceQuestion(String questionID, int quizID, String questionText, String hint) {
        super(questionID, quizID, questionText, hint);
        this.questionType = 1;
    }

    public List<String> getIncorrectChoices() {
        return incorrectChoices;
    }

    public void setIncorrectChoices(List<String> incorrectChoices) {
        this.incorrectChoices = incorrectChoices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public float gradeAnswer(String userAns) {
        return userAns.equals(correctAnswer) ? 1 : 0;
    }

    public String toString() {
        String id = "qid: " + questionID + "\n";
        String quizid = "quizid: " + quizID + "\n";
        String qText = "questionText: " + questionText + "\n";
        String answer = "answer: " + correctAnswer + "\n";
        String topi = "topic: " + topic + "\n";
        String qtype = "type: " + questionType + "\n";
        String inchoices = "backText: " + incorrectChoices.toString() + "\n";
        String stringCat = id + quizid + qText + answer + topi + qtype + inchoices;
        return stringCat;
    }
}
