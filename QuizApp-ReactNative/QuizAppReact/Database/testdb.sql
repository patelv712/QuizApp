-- MySQL dump 10.13  Distrib 8.0.29, for macos12 (arm64)
--
-- Host: localhost    Database: testdb
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- --
-- -- Table structure for table `testing`
-- --

-- DROP TABLE IF EXISTS `testing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS user_authentication (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('student', 'teacher', 'admin') NOT NULL,
    -- Add other user-related fields as needed
    profile_picture VARCHAR(255),
    contact_details VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS chapter (
    chapter_id INT AUTO_INCREMENT PRIMARY KEY,
    chapter_title VARCHAR(255) NOT NULL,
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

CREATE TABLE IF NOT EXISTS question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    answer_text TEXT, -- For flashcards
    chapter_id INT,
    FOREIGN KEY (chapter_id) REFERENCES chapter(chapter_id)
);

CREATE TABLE IF NOT EXISTS user_question_interaction (
    interaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    question_id INT,
    interaction_type ENUM('viewed', 'saved', 'time_spent') NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_authentication(user_id),
    FOREIGN KEY (question_id) REFERENCES question(question_id)
);

CREATE TABLE IF NOT EXISTS quiz (
    quiz_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_name VARCHAR(255) NOT NULL,
    chapter_id INT,
    FOREIGN KEY (chapter_id) REFERENCES chapter(chapter_id)
);
CREATE TABLE IF NOT EXISTS quiz_question (
    quiz_question_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_id INT,
    question_order INT, -- To specify the order of questions in a quiz
    FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id),
    FOREIGN KEY (question_id) REFERENCES question(question_id)
);
CREATE TABLE IF NOT EXISTS flashcard_deck (
    deck_id INT AUTO_INCREMENT PRIMARY KEY,
    deck_name VARCHAR(255) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user_authentication(user_id)
);
CREATE TABLE IF NOT EXISTS flashcard (
    flashcard_id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    deck_id INT,
    review_due_date DATE, -- For spaced repetition scheduling
    FOREIGN KEY (question_id) REFERENCES question(question_id),
    FOREIGN KEY (deck_id) REFERENCES flashcard_deck(deck_id)
);
/*!40101 SET character_set_client = @saved_cs_client */;

-- some basic questions
INSERT INTO question (question_text, answer_text, chapter_id) VALUES 
('What is the capital of France?', 'Paris', 1),
('What is 2 + 2?', '4', 1),
('Which planet is known as the Red Planet?', 'Mars', 2),
('Which gas do plants absorb from the atmosphere?', 'Carbon Dioxide', 2),
('What is the largest mammal?', 'Blue Whale', 3),
('Which element has the symbol "K"?', 'Potassium', 3),
('Who wrote "Romeo and Juliet"?', 'William Shakespeare', 4),
('Which country is known as the Land of the Rising Sun?', 'Japan', 4),
('Which river is the longest in the world?', 'Nile', 5),
('What is the smallest prime number?', '2', 5),
('Which planet has a ring around it?', 'Saturn', 6),
('Which animal is known as the King of the Jungle?', 'Lion', 6),
('What is the main component of Earth\'s atmosphere?', 'Nitrogen', 7),
('Who painted the Mona Lisa?', 'Leonardo da Vinci', 7),
('Which metal is heavier, silver or gold?', 'Gold', 8),
('How many planets are in our solar system?', '8', 8),
('Which planet is closest to the sun?', 'Mercury', 9),
('What is the capital of Italy?', 'Rome', 9),
('Which element has the symbol "O"?', 'Oxygen', 10),
('Which gas is most abundant in Earth\'s atmosphere?', 'Nitrogen', 10),
('Which planet is known for its rings?', 'Saturn', 11),
('What is the largest planet in our solar system?', 'Jupiter', 11),
('What is the chemical symbol for gold?', 'Au', 12),
('Who wrote "Pride and Prejudice"?', 'Jane Austen', 12),
('Which bone is the longest in the human body?', 'Femur', 13),
('What is the name of the ship that sank in 1912?', 'Titanic', 13),
('Which country is known as the Land Down Under?', 'Australia', 14),
('Which is the tallest mountain in the world?', 'Mount Everest', 14),
('What is the main gas found in the air we breathe?', 'Nitrogen', 15),
('Which is the smallest ocean in the world?', 'Arctic Ocean', 15),
('Which is the biggest desert in the world?', 'Antarctica', 16),
('Who discovered penicillin?', 'Alexander Fleming', 16),
('What is the capital of Spain?', 'Madrid', 17),
('Which planet is known as the Evening Star?', 'Venus', 17),
('What is the chemical symbol for silver?', 'Ag', 18),
('Who is the author of the Harry Potter series?', 'J.K. Rowling', 18),
('Which gas do plants release into the atmosphere?', 'Oxygen', 19),
('What is the currency used in Japan?', 'Yen', 19),
('Which river flows through Egypt?', 'Nile', 20),
('How many bones are there in an adult human body?', '206', 20),
('What is the name of the largest ocean?', 'Pacific Ocean', 21),
('Which country is the largest by land area?', 'Russia', 21),
('Who is known as the Father of Evolution?', 'Charles Darwin', 22),
('What is the study of plants called?', 'Botany', 22),
('Which is the smallest country by land area?', 'Vatican City', 23),
('What is the currency of the United States?', 'Dollar', 23),
('What is the chemical symbol for water?', 'H2O', 24),
('Who is the fastest land animal?', 'Cheetah', 24),
('Which is the largest planet?', 'Jupiter', 25),
('Which is the smallest continent by land area?', 'Australia', 25);

-- ee related questions
INSERT INTO question (question_text, answer_text, chapter_id) VALUES 
('What is the unit of resistance?', 'Ohm', 1),
('Which law states V=IR?', 'Ohm’s Law', 1),
('What type of material has atoms with tightly bound electrons?', 'Insulator', 2),
('Which component is used to store energy in an electrostatic field?', 'Capacitor', 2),
('What is the basic unit of electric current?', 'Ampere', 3),
('Which device converts mechanical energy into electrical energy?', 'Generator', 3),
('Which law relates magnetic field to the current?', 'Ampère’s Law', 4),
('What is the opposition to AC current called?', 'Impedance', 4),
('What is the maximum power transfer theorem?', 'The power transferred to a load is maximum when the impedance of the load matches the source impedance.', 5),
('Which device measures potential difference between two points?', 'Voltmeter', 5),
('What component restricts the flow of current?', 'Resistor', 6),
('Which material has very low resistance?', 'Conductor', 6),
('What is the charge of an electron?', '-1.6 x 10^-19 Coulombs', 7),
('Which theorem provides a method to determine current in any circuit branch?', 'Kirchhoff’s Current Law', 7),
('Which device converts electrical energy into mechanical energy?', 'Motor', 8),
('Which component stores energy in a magnetic field?', 'Inductor', 8),
('What does AC stand for in electrical engineering?', 'Alternating Current', 9),
('What type of transformer is used to step up or step down voltages?', 'Power Transformer', 9),
('What is the frequency of AC power in North America?', '60 Hz', 10),
('What does LED stand for?', 'Light Emitting Diode', 10),
('Which network theorem allows sources to be replaced by a single voltage source and series resistance?', 'Thevenin’s Theorem', 11),
('What does PCB stand for?', 'Printed Circuit Board', 11),
('What type of waveform is used in most power systems worldwide?', 'Sine Wave', 12),
('What is the color code for a 1k ohm resistor?', 'Brown-Black-Red', 12),
('What component is used to protect circuits from overcurrent?', 'Fuse', 13),
('Which law states that the induced electromotive force (or "E.M.F.") in any closed circuit is equal to the negative of the time rate of change of the magnetic flux enclosed by the circuit?', 'Faraday’s Law', 13),
('Which instrument measures electrical resistance?', 'Ohmmeter', 14),
('What is the unit of frequency?', 'Hertz', 14),
('Which type of materials exhibit magnetism only in the presence of an external magnetic field?', 'Paramagnetic Materials', 15),
('Which circuit law states that the directed sum of the electrical current entering a node (or a junction) equals zero?', 'Kirchhoff’s Current Law', 15),
('What is the primary purpose of a rectifier?', 'Convert AC to DC', 16),
('In a transformer, what causes losses due to the resistance of the windings?', 'Copper Losses', 16),
('What type of magnetic materials are attracted weakly to magnets?', 'Diamagnetic Materials', 17),
('What is the speed of light in a vacuum?', '299,792,458 meters per second', 17),
('What is the typical value of power factor for industrial loads?', '0.8 lagging', 18),
('What type of circuit is characterized by having its outputs depend only on the current inputs?', 'Combinational Circuit', 18),
('What do you call a material that has resistance which decreases with the increase of temperature?', 'Negative Temperature Coefficient (NTC) Thermistor', 19),
('What is the function of a Zener diode?', 'Voltage Regulation', 19),
('What is the ability of a component to oppose the change in current called?', 'Inductance', 20),
('Which circuit element has the property of reactance?', 'Inductor and Capacitor', 20),
('Which type of machine is the Tesla coil?', 'Resonant Transformer', 21),
('What is the common base, emitter, and collector for a Bipolar Junction Transistor?', 'Emitter is the common terminal', 21),
('Which type of diode is designed specifically for operations in the reverse breakdown region?', 'Zener Diode', 22),
('What is a closed path in which electrons from a voltage or current source flow?', 'Electric Circuit', 22),
('What is the phenomenon where the voltage induced in a loop by a change in the strength of magnetic field is such that it opposes the change in current?', 'Lenz’s Law', 23),
('What type of electrical component removes electrical signals of undesired frequencies from a signal?', 'Filter', 23),
('What law states that the voltage across a capacitor is directly proportional to the electric charge stored on it?', 'Coulomb’s Law', 24),
('Which device controls the flow of current in an electrical circuit?', 'Switch', 24),
('Which logic gate outputs true or HIGH only when both of its inputs are true or HIGH?', 'AND Gate', 25),
('What type of source supplies a constant voltage?', 'Voltage Source', 25);

--
-- Dumping data for table `testing`
--

/*!40000 ALTER TABLE `testing` DISABLE KEYS */;
/*!40000 ALTER TABLE `testing` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-13 16:54:10
