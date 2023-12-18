package edu.gatech.seclass.quizapp;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    public int quizID = -1;
    public int questionCounter = -1;
    public List<Question> questions = new ArrayList<>();

    public int addQuestionToQuiz(Question question) {
        questions.add(question);
        return questions.size();
    }

    public int numberOfQuestions() {
        return questions.size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /*
    getNextQuestion returns a Question object
    It increments the questionCounter
    Then it returns the Question at index questionCounter in questions
    If questionCounter >= number of questions in questions,
    returns null instead
     */
    public Question getCurrentQuestion() {
        questionCounter++;
        if (questionCounter >= questions.size()) {
            return null;
        }
        return questions.get(questionCounter);
    }

    /*
    peekNextQuestion returns an int
    It looks for a question at index questionCounter+1
    If we are at the end of the list, returns "End of List"
    Otherwise, returns the questionType of the next question
    (1 = MultipleChoice, 2 = ShortAnswer, 3 = Flashcard
     */
    public int peekNextQuestionType() {
        if (questionCounter + 1 >= questions.size()) {
            return -1;
        } else {
            return questions.get(questionCounter + 1).questionType;
        }
    }
}