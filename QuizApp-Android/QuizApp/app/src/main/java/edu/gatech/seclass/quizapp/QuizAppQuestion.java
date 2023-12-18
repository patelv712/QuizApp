package edu.gatech.seclass.quizapp;
import java.util.Date;

/**
 * Class describing the attributes of a question attempt that is used in the spaced repetition
 * algorithm. The question will always be a flashcard question.
 */

public class QuizAppQuestion {
    public String id;
    public String question;
    public String answer;
    public int chapter;
    public int section;

    public QuizAppQuestion(String id, String question, String answer,
                           int chapter, int section) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.chapter = chapter;
        this.section = section;
    }
}
