const mongoose = require('mongoose');

const QuestionSchema = new mongoose.Schema({
	questionId: String,
	quizId: Number,
	questionText: String,
	correctAnswer: String,
	topic: String,
	questionType: Number,
	backText: String,
	incorrectChoices: [String],
	hint: String,
});
module.exports = mongoose.model('Question', QuestionSchema, 'Question');
