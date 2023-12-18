const mongoose = require('mongoose');

const QuizAppQuestionSchema = new mongoose.Schema({
	questionId: String,
	question: String,
	answer: String,
	chapter: Number,
	section: Number,
});
module.exports = mongoose.model(
	'QuizAppQuestion',
	QuizAppQuestionSchema,
	'QuizAppQuestion'
);
