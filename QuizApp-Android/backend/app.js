const express = require('express');
const { send, json } = require('express/lib/response');
const app = express();
const mongoose = require('mongoose');
const Question = require('./Question.js');
const UserAnswer = require('./UserAnswer.js');
const QuizAttempt = require('./QuizAttempt.js');
const User = require('./User.js');
const { count } = require('./Question.js');

// Used for Fall 2022
const QuizAppQuestion = require('./QuizAppQuestion.js');
const QuestionAttempt = require('./QuestionAttempt.js');
const { ObjectId } = require('mongodb');
app.use(express.json());

//test function to see if app is listening on port 3000
app.get('/test', function (req, res) {
	res.send('test works');
});

//connection to database
const url =
	'mongodb+srv://quizAppMobileUser:xkLaBBaa1dTsZuX8@quizappdb.7ltdg.mongodb.net/quizAppDummyData';
const quizAppQuestionUrl =
	'mongodb+srv://quizAppMobileUser:xkLaBBaa1dTsZuX8@quizappdb.7ltdg.mongodb.net/QuizAppQuestions';

const connectionParams = {
	useUnifiedTopology: true,
	useNewUrlParser: true,
};
mongoose
	.connect(quizAppQuestionUrl, connectionParams)
	.then(() => {
		console.log('connected to mongodb atlas cluster');
	})
	.catch((err) => {
		console.log('could not connect cuz ', err);
		console.log('exiting');
		process.exit();
	});

// Create a reference to the database
var db = mongoose.connection;

/**
 * Queries for QuizAppSchema
 */

/**
 * GET: /readChapterQuestions/:chapterNumber/:count
 * Retrieves random set of <count> question from questions from chapter <chapterNumber>.
 */
app.get('/readChapterQuestions/:type/:chapterNumber/:count', (req, res) => {
	QuizAppQuestion.find({ chapter: req.params.chapterNumber })
		.then((questions) => {
			if (req.params.type == 'rand') {
				numberOfQuestions = Math.min(req.params.count, questions.length - 1);
				randNumSet = [];
				questionsToReturn = [];

				for (i = 0; i < numberOfQuestions; i++) {
					newNum = randomIntFromInterval(0, numberOfQuestions);
					while (randNumSet.includes(newNum)) {
						newNum = randomIntFromInterval(0, numberOfQuestions);
					}
					randNumSet.push(newNum);
					questions[newNum] != undefined
						? questionsToReturn.push(questions[newNum])
						: NaN;
				}
				console.log(
					questionsToReturn.forEach((question) => {
						console.log(
							'ID: ' +
								question.id +
								', ' +
								question.question +
								' \nAns: ' +
								question.answer
						);
					})
				);
				console.log(questionsToReturn.length + ' questions returned');
				res.send(questionsToReturn);
			} else {
				questions = questions.splice(
					0,
					Math.min(req.params.count, questions.length)
				);
				res.send(questions);
			}
		})
		.catch((err) => {
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
				Error: err,
			});
		});
});

/**
 * GET: /readChapterQuestionsFromSection/:chapterNumber/:section/:count
 * Retrieves random set of <count> question from questions from chapter <chapterNumber> and <section>.
 */
app.get(
	'/readChapterQuestions/:type/:chapterNumber/:section/:count',
	(req, res) => {
		QuizAppQuestion.find({
			chapter: req.params.chapterNumber,
			section: req.params.section,
		})
			.then((questions) => {
				if (req.params.type == 'rand') {
					numberOfQuestions = Math.min(req.params.count, questions.length - 1);
					randNumSet = [];
					questionsToReturn = [];

					for (i = 0; i < numberOfQuestions; i++) {
						newNum = randomIntFromInterval(0, numberOfQuestions);
						while (randNumSet.includes(newNum)) {
							newNum = randomIntFromInterval(0, numberOfQuestions);
						}
						randNumSet.push(newNum);
						questions[newNum] != undefined
							? questionsToReturn.push(questions[newNum])
							: NaN;
					}
					res.send(questionsToReturn);
				} else {
					questions = questions.splice(
						0,
						Math.min(req.params.count, questions.length)
					);
					res.send(questions);
				}
			})
			.catch((err) => {
				console.log(err);
				res.status(500).send({
					message: 'some error occurred while retrieving messages',
					Error: err,
				});
			});
	}
);

/**
 * GET: /getParticularQuestion/:ID
 * Retrieves a question with a given ID.
 */
app.get('/getParticularQuestion/:ID', (req, res) => {
	QuizAppQuestion.findById(req.params.ID).then((question) => {
		console.log(question);
		res.send(question);
	});
});

/**
 * GET: /findQuestionAttempt/:userId/:qId
 * Retrieves question attempt matching the userID and the questionID.
 */
app.get('/findQuestionAttempt/:userId/:qId', (req, res) => {
	QuestionAttempt.find({
		userId: req.params.userId,
		questionId: req.params.qId,
	})
		.then((questionAttempt) => {
			if (questionAttempt.length == 0) {
				const currentDate = new Date();
				var newQuestionAttempt = {
					questionId: req.params.qId,
					userId: req.params.userId,
					repetitions: 0,
					easeFactor: 2.5,
					interval: currentDate,
				};
				console.log(newQuestionAttempt);
				db.collection('QuestionAttempt').insert(newQuestionAttempt);
				res.send(newQuestionAttempt);
			}
			res.send(questionAttempt);
		})
		.catch((err) => {
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
				Error: err,
			});
		});
});

/**
 * GET: /updateQuestionAttempt/:questionAttemptId/:quality
 * Updates a question attempt based on the quality outputted from the user
 */

app.get(
	'/updateQuestionAttempt/:questionAttemptId/:quality',
	async (req, res, next) => {
		var quality = req.params.quality;
		const attempt = await db
			.collection('QuestionAttempt')
			.findOne({ _id: ObjectId(req.params.questionAttemptId) });
		console.log(attempt);
		if (quality >= 3) {
			var interval = 0;
			if (attempt.repetitions == 0) {
				interval = 1;
			} else if (attempt.repetitions == 1) {
				interval = 6;
			} else {
				const currentDate = new Date();
				interval = Math.ceil(
					Math.round(
						(attempt.interval.getTime() - currentDate.getTime()) /
							(1000 * 3600 * 24)
					) * attempt.easeFactor
				);
			}
			attempt.interval.setDate(attempt.interval.getDate() + interval);
			attempt.interval = attempt.interval;
			attempt.repetitions += 1;
			attempt.easeFactor += 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02);
		} else if (quality < 3) {
			attempt.repetitions = 0;
			attempt.interval.setDate(attempt.interval.getDate() + 1);
		}
		attempt.easeFactor = Math.max(attempt.easeFactor, 1.3);
		QuestionAttempt.updateOne({ _id: req.params.questionAttemptId }, attempt)
			.then(() => {
				res.send(attempt);
			})
			.catch((error) => {
				res.status(400).json({
					error: error,
				});
			});
	}
);

// OLD Questions schema
// gets all questions
app.get('/read', (req, res) => {
	Question.find()
		.then((questions) => {
			res.send(questions);
			console.log(questions);
		})
		.catch((err) => {
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
			});
		});
});

//gets a set of 5 questions of each type
app.get('/readrandom', (req, res) => {
	console.log('came here');
	allQs = [];
	const randomQs = [];
	Question.find()
		.then((questions) => {
			questions.forEach((question) => {
				allQs.push(question);
			});
			//generating 5 random indexes that will pick random questions, but will have spread out type of questions
			var randx1 = randomIntFromInterval(0, 4);
			var randx3 = randomIntFromInterval(5, 9);
			var randx2 = randomIntFromInterval(10, 14);
			//decide which types of questions to pick last two from
			var last1 = randomIntFromInterval(1, 3);
			var last2 = randomIntFromInterval(1, 3);
			while (last2 === last1) {
				last2 = randomIntFromInterval(1, 3);
			}
			var randx4 = 0;
			switch (last1) {
				case 1:
					randx4 = randomIntFromInterval(0, 4);
					while (randx4 === randx1) {
						randx4 = randomIntFromInterval(0, 4);
					}
					break;
				case 2:
					randx4 = randomIntFromInterval(10, 14);
					while (randx4 === randx2) {
						randx4 = randomIntFromInterval(10, 14);
					}
					break;
				case 3:
					randx4 = randomIntFromInterval(5, 9);
					while (randx4 === randx3) {
						randx4 = randomIntFromInterval(5, 9);
					}
			}
			var randx5 = 0;
			switch (last2) {
				case 1:
					randx5 = randomIntFromInterval(0, 4);
					while (randx5 === randx1) {
						randx5 = randomIntFromInterval(0, 4);
					}
					break;
				case 2:
					randx5 = randomIntFromInterval(10, 14);
					while (randx5 === randx2) {
						randx5 = randomIntFromInterval(10, 14);
					}
					break;
				case 3:
					randx5 = randomIntFromInterval(5, 9);
					while (randx5 === randx3) {
						randx5 = randomIntFromInterval(5, 9);
					}
			}
			if (allQs[randx1] != null) {
				randomQs.push(allQs[randx1]);
			}
			if (allQs[randx2] != null) {
				randomQs.push(allQs[randx2]);
			}
			if (allQs[randx3] != null) {
				randomQs.push(allQs[randx3]);
			}
			if (allQs[randx4] != null) {
				randomQs.push(allQs[randx4]);
			}
			if (allQs[randx5] != null) {
				randomQs.push(allQs[randx5]);
			}

			res.send(randomQs);
			console.log(randomQs);
			console.log('success!');
		})
		.catch((err) => {
			console.log('failed');
			res.status(500).send({
				message: err,
			});
		});
});

function randomIntFromInterval(min, max) {
	// min and max included
	return Math.floor(Math.random() * (max - min + 1) + min);
}

//gets 5 questions of one topic
//TODO: make it so that the number of questions of each type in the list
//sent corresponds to the probabilities sent
app.get('/readrecommended', (req, res) => {
	const topicQ = 'topic1';
	console.log('topic1 querying');
	Question.find({ topic: topicQ })
		.then((questions) => {
			res.send(questions);
			console.log(questions);
		})
		.catch((err) => {
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
			});
		});
});

//writes a question into the database, for testing
app.post('/post', async function (req, res) {
	console.log(req.body);
	const question = new Question({
		quizId: req.body.quizId,
		questionText: req.body.questionText,
		correctAnswer: req.body.correctAnswer,
		topic: req.body.topic,
		questionType: req.body.questionType,
		backText: req.body.backText,
		incorrectChoices: req.body.incorrectChoices,
	});

	try {
		await question.save();
		console.log('i tried');
		res.status(200).json({ success: true, message: 'question details saved' });
	} catch (err) {
		res
			.status(500)
			.json({ success: false, message: 'Error in saving question details' });
	}
});

app.get('/isUserUnique', async function (req, res) {
	User.find()
		.then((users) => {
			users.forEach((user) => {
				if (user.userId === req.body.userId) {
					throw new Error('username is not unique');
				}
			});
			res.status(200).json({ success: true, message: 'user unique' });
		})
		.catch((err) => {
			res.send({ success: false, error: err });
		});
});

//writes user object into database
app.post('/recordUser', async function (req, res) {
	const userReq = new User({
		userId: req.body.userId,
		password: req.body.password,
		name: req.body.name,
	});
	var allUsers = [];
	User.find()
		.then(async (users) => {
			users.forEach((user) => {
				if (user.userId === userReq.userId) {
					console.log(isUnique + 'username unique');
					res.status(400).send({
						message: 'username must be unique',
					});
					throw new Error();
				}
			});
			await userReq.save();
			res.status(200).json({ message: 'success' });
		})
		.catch((err) => {
			console.log('came here');
			res.status(400).json({ message: 'success' });
		});
	// try{
	//     await user.save();
	//     res.status(200).json({ "message":"success"});
	// }
	// catch(err) {
	//     res.status(500).json({ "message":"Error in saving user details", "error": err});
	// }
});

//updates user password
app.post('/updatePassword/:userId/:password', async function (req, res) {
	try {
		await User.findOneAndUpdate(
			{ userId: req.params.userId },
			{ password: req.params.password }
		);
		res.status(200).send({
			message:
				'question with id: ' + req.params.userId + ' updated successfully',
		});
	} catch (err) {
		res.status(500).send({
			message: 'some error occurred while retrieving messages',
			error: err,
		});
	}
});

//updates user's name
app.post('/updateUserName/:userId/:name', async function (req, res) {
	try {
		await User.findOneAndUpdate(
			{ userId: req.params.userId },
			{ name: req.params.name }
		);
		res.status(200).send({
			message:
				'question with id: ' + req.params.userId + ' updated successfully',
		});
	} catch (err) {
		res.status(500).send({
			message: 'some error occurred while retrieving messages',
			error: err,
		});
	}
});

//retrieves user
app.get('/getUser/:userId', async function (req, res) {
	User.findOne({ userId: req.params.userId })
		.then((user) => {
			res.send(user);
			console.log(user);
		})
		.catch((err) => {
			console.log(err);
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
				error: err,
			});
		});
});

//retrieves all users

//just for convenience
app.put('/updateQuestionId/:id/:questionId', async function (req, res) {
	console.log('i got here');
	console.log(req.params);
	console.log(mongoose.Types.ObjectId.isValid(req.params.id));
	Question.findByIdAndUpdate(req.params.id, {
		questionId: req.params.questionId,
	})
		.then(() => {
			res.status(200).send({
				message: 'question with id: ' + req.params.id + ' updated successfully',
			});
		})
		.catch((err) => {
			console.log(err);
			res.status(500).send({
				message: 'some error occurred while retrieving messages',
			});
		});
});

app.post('/recordAnswers', async function (req, res) {
	//create QuizAttempt object
	quizattempt = createQuizAttempt(req, res);
	try {
		await quizattempt.save();
		console.log('successfully recorded quizAttempt');
		res
			.status(200)
			.json({ message: 'User details saved', objectSaved: quizattempt });
	} catch (err) {
		res
			.status(500)
			.json({ message: 'Error in saving user details', error: err });
	}
});

const createQuizAttempt = (req, res) => {
	useranswerlist = [];
	console.log(req.body);
	req.body.useranswers.forEach((useranswer) => {
		const answer = new UserAnswer({
			questionId: useranswer.questionId,
			answerText: useranswer.answerText,
			timeStamp: useranswer.timeStamp,
			topic: useranswer.topic,
			score: useranswer.score,
		});
		useranswerlist.push(answer);
	});
	const quizattempt = new QuizAttempt({
		userId: req.body.userId,
		quizId: req.body.quizId,
		quizAttemptId: req.body.quizAttemptId,
		timeStamp: req.body.timeStamp,
		useranswers: useranswerlist,
		score: req.body.score,
	});
	return quizattempt;
};

app.listen(3000, () => console.log('Listening on port 3000'));
