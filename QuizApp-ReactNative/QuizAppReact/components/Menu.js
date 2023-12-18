import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const MenuPage = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Menu</Text>
      <TouchableOpacity
        style={[styles.button, styles.settingsButton]}
        onPress={() => navigation.navigate('SettingsScreen')}
      >
        <Text style={styles.buttonText}>Settings</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={[styles.button, styles.flashcardsButton]}
        onPress={() => navigation.navigate('FlashScreen')}
      >
        <Text style={styles.buttonText}>Flashcards</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={[styles.button, styles.quizzesButton]}
        onPress={() => navigation.navigate('Quizzes')}
      >
        <Text style={styles.buttonText}>Quizzes</Text>
      </TouchableOpacity>


      <TouchableOpacity
        style={[styles.button, styles.searchButton]}
        onPress={() => navigation.navigate('SearchScreen')}
      >
        <Text style={styles.buttonText}>Search</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={[styles.button, styles.progressButton]}
        onPress={() => navigation.navigate('Progress')}
      >
        <Text style={styles.buttonText}>Progress</Text>
      </TouchableOpacity>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#007AFF', // Blue background to match FlashcardScreen
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  backButton: {
    position: 'absolute',
    top: 40, // Adjust based on your status bar height
    left: 10,
    backgroundColor: '#000', // This is a typical iOS blue color
    padding: 10,
    borderRadius: 20, // Half of your desired diameter
    zIndex: 10, // Make sure the button is clickable over other elements
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#FFFFFF', // White color for the title text
    marginBottom: 40,
  },
  button: {
    width: '80%',
    padding: 15,
    borderRadius: 10,
    marginVertical: 10,
    alignItems: 'center',
    // Default button color; will be overridden by specific button styles
    backgroundColor: '#FFFFFF',
  },
  settingsButton: {
    backgroundColor: '#FFA500', // Orange color for Settings
  },
  flashcardsButton: {
    backgroundColor: '#1E90FF', // Blue color for Flashcards
  },
  quizzesButton: {
    backgroundColor: '#FF4500', // Red color for Quizzes
  },
  searchButton: {
    backgroundColor: '#FFD700', // Yellow color for Saved Questions
  },
  progressButton: {
    backgroundColor: '#FFC0CB', // Pink color for Progress
  },
  buttonText: {
    color: '#FFFFFF', // White color for button text
    fontSize: 18,
  },
});

export default MenuPage;
