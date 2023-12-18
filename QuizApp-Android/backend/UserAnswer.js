const mongoose = require('mongoose');

const UserAnswerSchema = new mongoose.Schema({
    questionId: String, 
    answerText: String,
    timeStamp: Date,
    topic: String,
    score: Number
});
module.exports = mongoose.model('UserAnswer', UserAnswerSchema, "UserAnswer");