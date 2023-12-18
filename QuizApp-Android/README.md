# QuizApp - Fall 2022

## Setup

**App:** We used Android Studio to develop the app. After downloading Android Studio and cloning the repo, load the QuizApp folder into Android Studio as a project. You likely will need to “Clear Caches and Restart” at this time. Add a virtual device of your choosing, using R as the Android version for the device. An excellent tutorial on getting started with Android Studio can be found here: https://youtu.be/jqB3r_16WqA

**Database:** We used a MongoDB Atlas cluster to store the information related to the classes in the UML diagram. To access the database, download MongoDB Compass [here](https://www.mongodb.com/try/download/compass). Since this is a cloud-based database, there is no need to download mongo itself. Connect to the database with the connection string:

- `mongodb+srv://quizAppMobileUser:xkLaBBaa1dTsZuX8@quizappdb.7ltdg.mongodb.net/test`

Each class is represented as a Collection and each object of that class is represented as a Document within that collection.

**API:** Make sure npm is installed. In your terminal, cd into backend, and run "npm install". This should download all the node modules with the versions specified in package-lock.json. Then, run "npm start". This should output "listening on port 3000, connected to the database" onto the terminal. 

*Ensuring QuizApp-API Integration works:* The API is currently locally hosted on port 3000, which is on a different host than the Android emulator. This means that you must change the IP address in the URL used in the Volley requests to the IP address of your public Wifi network. For example, "http://yourip:3000/readrandom". This will need to be done in the BrowseQuizzes, ResultsActivity, Login, CreateAccount, FlashCardBackActivity2, and ForgotPassword classes. However, this can be done by only changing the instance variable `ipa` in the Login class as this string replaces all the lines of code that use the IP address. It may be possible that the ID address you give could be the IP address of the emulator itself and not the local machine. One way to avoid problems from this is to just set "yourip" in "http://yourip:3000/readrandom" to be 10.0.2.2 which defaults to the correct local machine ip address.

## Front End

<details> 
  <summary><b><i>Layout XML Files</b></i></summary>
  
  #### Note: the arrows indicate what page from the `QuizApp\app\src\main\res\layout` folder is operated by each `.java` file from the `QuizApp\app\src\main\java\edu\gatech\seclass\quizapp` folder.
  
  #### `Login.java` &rarr; `activity_login_page.xml`
  This class allows the user to login. The corresponding layout file is `res/layout/activity_login_page.xml`. If the credential validation says that this username, and password pair exists in the backend, then it’ll load up that User object and let the user through. 

  #### `CreateAccount.java` &rarr; `activity_create_account.xml`
  This class allows the user to create an account. The corresponding layout file is `res/layout/activity_create_account.xml`. The User can input their account information, such as name, birthdate, email, username, password, and school. They can also uploade a picture of themselves as a profile picture.

  #### `ForgetPassword.java` &rarr; `activity_forget_pass.xml`
  This class allows the user to reset their credentials. The corresponding layout file is `res/layout/activity_forget_pass.xml`. It is accessible through the Login page incase the user's credentials don't match up with what is stored in the backend. 

  #### `MainActivity.java` &rarr; `activity_main.xml`
  This class powers the home page of the app. The corresponding layout file is `res/layout/activity_main.xml`. The main activity is launched when the user launches the app. If the global `CurrentUser` object is not instantiated, then the user is redirected to the login page. If the `CurrentUser` object is instantiated, then the Home page is shown with three buttons: Browse Quizzes, Analytics, and Log Out.

  #### `BrowseQuizzes.java` &rarr; `activity_browse_quizzes.xml` & `list_view_search_bar.xml`
  By the `onCreate()` method, this class launches the `activity_browse_quizzes.xml` page by setting up the necessary variables and initializing the hard-coded list of quizess to be displayed as search results for the search bar. It also handles different interactions the user makes with different UI elements like the search bar, quiz buttons, and home button. To display the search results, a `ListView` object is used in which the formatting of each result is controlled by the `list_view_search_bar.xml` file.

  #### `MultipleChoiceActivity.java` &rarr; `activity_multiplechoice_question.xml`
  Controls functinality of the multiple choice answer question type. 

  #### `FlashCardFrontActivity.java` & `FlashCardFrontActivity2.java` &rarr; `activity_flashcard_front.xml`
  Controls functinality of the front of the flashcard type question type. 

  #### `FlashCardBackActivity.java` & `FlashCardBackActivity2.java` &rarr; `activity_flashcard_back.xml`
  Controls functinality of the back of the flashcard type. 

  #### `ShortAnswerActivity.java` &rarr; `activity_short_answer.xml`
  Controls functinality of the short answer question type. 

  #### `HintActivity.java` &rarr; `activity_hint_page.xml`
  This hint activity is in place so that users can get help on questions when they are taking a quiz. Once the help/hint button is clicked on from the quiz, users are prompted to the hint page where they can see a hint for the question they are on. Future work on this page includes backend support for personalized hint development. 

  #### `ResultsActivity.java` &rarr; `activity_results.xml`
  This class powers the Results page of the app. The corresponding layout file is `res/layout/activity_results.xml`. At the end of a quiz, the user is directed to the Results page. This is where the app makes an API request to submit the quiz attempt. This is potentially where the app would fetch skill recommendations from the knowledge tracing (KT) API, as a future development. The User is meant to be shown how many questions they got right out of the total questions in the quiz. They are also shown recommended skills to study next, based on the results of the KT API calls.

  #### `AnalyticsActivity.java` &rarr; `activity_analytics.xml`
  This class powers the analytics page of the app. The corresponding layout file is `res/layout/activity_analytics.xml`. From the home screen, the user can go to the analytics screen to see insight in their progress. Right now, everything on the analytics screen are placeholders to be implemented.
  
  #### `WelcomeActivity.java` &rarr; `activity_welcome.xml`
  This class powers the welcome page of the app. The corresponding layout file is `res/layout/activity_welcome.xml`. This is the screen that the user sees when the app launches.
</details>


<details> 
  <summary><b><i>Java Files</b></i></summary>
  
  #### `User.java`
  This class stores user information. It is instantiated and populated upon login. When the user is currently working on a quiz, the currentQuiz holds that quiz. At that time, the `currentQuizAttempt` holds the users answers to that quiz.

  #### `UserAnswer.java`
  This class stores information about a user's attempt on a particular question during a particular quiz.

  #### `Quiz.java`
  This class holds a `Quiz` object, which consists of a list of `Question` objects. The `Quiz` object is loaded by the `Controller` class from the MongoDB backend. The `getCurrentQuestion` method is meant to get the current question so it can be displayed. The `peekNextQuestion` method is meant to peek at the next question in order to determine its type so that the appropriate screen can be shown to the User.
  
  #### `QuizAppQuiz.java`
  (TBD)

  #### `QuizAttempt.java`
  This class stores information about a user's particular quiz session. It contains an aggregation of UserAnswer objects. 

  #### `Question.java`
  The `Question` class is a superclass for each of the question types. It has information such as question ID and question text.
  
  #### `QuizAppQuestion.java`
  (TBD)

  #### `FlashCardQuestion.java`
  The `FlashCardQuestion` class is a subclass of `Question`. It has a front and back of the card. It also requires the user to self-report if they got the flashcard right or wrong.

  #### `ShortAnswerQuestion.java`
  The `ShortAnswerQuestion` class is a subclass of `Question`. Grading an answer would be done by comparing the text to the correct answer.

  #### `MultipleChoiceQuestion.java`
  The `MultipleChoiceQuestion` class is a subclass of `Question`.

  #### `SingletonRequestQueue.java`
  This class maintains a single global instance of a request dispatch queue that connects to the network and implements a cache policy. Any Volley request made from any screen during the lifetime of the app will be queued into a single RequestQueue object that will dispatch the requests to the API. 

  #### `JsonUtil.java`
  This class contains util methods for JSON parsing and conversion, used to process the data being sent and recieved from the API. 
</details>

## Back End 

### Overview
The backend is written in a Node.js runtime environment, using the ExpressJS and Mongoose libraries. On a high-level, the Android QuizApp will make requests to the API to store/retrieve data from the MongoDB database, the API will query/update the database accordingly, and send a response back to the QuizApp.

#### `app.js`
This file contains the endpoints that query/update the database according to the request received from QuizApp. Cleanup work involves reorganizing it into a model-controller-routes structure that is commonly used with this tech stack.

#### `UserAnswer.js`, `User.js`, `Question.js`, `QuizAttempt.js`
These files are the schemas/models for the appropriate classes/collections being used in the QuizApp and being stored in the database. There may be need for more schemas based on the types of endpoints written in the future.

#### `QuestionAttempt.js`, `QuizAppQuestion.js`, `QuizAppQuestions.js`
(TBD)

## Fall 2022 Team Members

- Varun Patel
- Samarth Parameswar
- Sanjit Pingili
- Sahana Krishnan
- Abdulaziz Memesh
- Rishi Nopany
- Shreekrishna Bhat

## [Figma design link](https://www.figma.com/file/cyFwvVE3NVjJ2dUXdFskBr/Quiz-App-Real?node-id=407%3A646&t=Y3GA2Te2pDjtn5sQ-3)
## [Google Drive documents link](https://drive.google.com/drive/folders/1YuEzk9bqs9OlKFzzpGeBEcoXmXfVXOD9?usp=sharing)
