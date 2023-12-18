import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

const AboutScreen = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={() => navigation.goBack()}
        style={styles.backButton}
      >
        <Icon name="arrow-back-circle" size={24} color="white" /> 
      </TouchableOpacity>
      <Text style={styles.versionText}>Version 1.0</Text>
      <Text style={styles.descriptionText}>
        This is the quiz app for VIP-ITS. It displays multiple choice questions,
        vocab flashcards, and short answers. It also recommends what the student
        should learn next.
      </Text>
    </View>
  );
};


const styles = StyleSheet.create({

  backButton: {
    position: 'absolute',
    top: 40, // Adjust based on your status bar height
    left: 10,
    backgroundColor: '#000', // This is a typical iOS blue color
    padding: 10,
    borderRadius: 20, // Half of your desired diameter
    zIndex: 10, // Make sure the button is clickable over other elements
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#B3DCF3',
  },
  versionText: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 15,
  },
  descriptionText: {
    fontSize: 18,
    color: '#888',
    textAlign: 'center',
    marginLeft: 20,
    marginRight: 20,
  },
});

export default AboutScreen;
