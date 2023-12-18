package edu.gatech.seclass.quizapp;

public class FlashCardQuestion extends Question {
    private String backOfCard;

    public FlashCardQuestion(String questionID, int quizID, String questionText, String hint) {
        super(questionID, quizID, questionText, hint);
        this.questionType = 3;
    }

    public String getBackOfCard() {
        return backOfCard;
    }

    public void setBackOfCard(String backOfCard) {
        this.backOfCard = backOfCard;
    }

    public String toString() {
        String id = "qid: " + questionID + "\n";
        String quizid = "quizid: " + quizID + "\n";
        String qText = "questiontext: " + questionText + "\n";
        String topi = "topic: " + topic + "\n";
        String qtype = "type: " + questionType + "\n";
        String back = "backText: " + backOfCard + "\n";
        String stringCat = id + quizid + qText + topi + qtype + back;
        return stringCat;
    }
}