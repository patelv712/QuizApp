package edu.gatech.seclass.quizapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describing the attributes of a quiz that is used when a quiz is opened.
 * The questions will always be flashcards.
 */

public class QuizAppQuiz {
    public List<QuizAppQuestion> questions = new ArrayList<>();
    public int size;
    public int currentIndex = 0;
    public QuizAppQuiz() {
        this.size = 0;
    }
    public void addQuestion(QuizAppQuestion attempt) {
        questions.add(attempt);
        size++;
    }
    public QuizAppQuestion getCurrentQuestion() {
        if (currentIndex >= size) {
            return null;
        }
        return questions.get(currentIndex);
    }
    public void incrementCurrentIndex() {
        currentIndex++;
    }
}
