const mongoose = require('mongoose');

const QuizAttemptSchema = new mongoose.Schema({
    userId: String,
    quizAttemptId: Number,
    quizId: Number,
    timeStamp: Date,
    useranswers: [],
    score: Number
});
module.exports =  mongoose.model('QuizAttempt', QuizAttemptSchema, "QuizAttempt");




