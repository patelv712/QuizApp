const mongoose = require("mongoose");

const QuizAppQuestionSchema = new mongoose.Schema({
  answer: String,
  chapter: String,
  question: String,
  section: String,
});
module.exports = mongoose.model("Data", QuizAppQuestionSchema, "Data");
