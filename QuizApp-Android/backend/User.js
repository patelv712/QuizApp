const mongoose = require('mongoose');
const QuizAttempt = require('./QuizAttempt');

const UserSchema = new mongoose.Schema({
    userId: String, 
    password: String, 
    name: String
});
module.exports = mongoose.model('User', UserSchema, "User");

