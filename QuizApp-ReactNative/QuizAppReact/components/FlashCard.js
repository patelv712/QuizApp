import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, SafeAreaView } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

const FlashcardScreen = ({ navigation }) => {
  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        {/* Header */}

        <TouchableOpacity
        onPress={() => navigation.goBack()}
        style={styles.backButton}
      >
        <Icon name="arrow-back-circle" size={24} color="white" /> 
      </TouchableOpacity>

        <View style={styles.header}>
          <Text style={styles.headerTitle}>Flashcards</Text>
        </View>

        {/* Flashcard */}
        <View style={styles.card}>
          <Text style={styles.cardText}>
            What is the pitch shown in fig.-xunkb for an A-440 tuning fork?
          </Text>
        </View>

        {/* Placeholder for Bottom Tab Bar or additional content */}
        {/* ... */}
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#007AFF', // Blue color to match the splash and login screens
  },
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#007AFF', // Ensuring the safe area and main content have the same background color
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
  header: {
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#007AFF', // Header background color
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#FFFFFF', // White color for the title text
  },
  card: {
    width: '80%',
    minHeight: 150,
    backgroundColor: '#FFFFFF', // White background for the flashcard
    borderRadius: 10,
    padding: 20,
    justifyContent: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.22,
    shadowRadius: 2.22,
    elevation: 3,
    // Ensure some margin at the top to prevent the card from touching the header
    marginTop: 20,
  },
  cardText: {
    textAlign: 'center',
    fontSize: 18,
    color: '#000000', // Dark text color for the card content
  },
  // Add styles for your bottom tab bar if needed
  // ...
});

export default FlashcardScreen;
