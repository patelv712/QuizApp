import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const SplashScreen = () => {
  return (
    <View style={styles.container}>
      <View style={styles.circle}>
        <Text style={styles.text}>Welcome to QuizApp</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#007AFF', // Replace with the actual background color
    alignItems: 'center',
    justifyContent: 'center',
  },
  circle: {
    backgroundColor: '#FFF', // Replace with the actual circle color
    height: 300, // Adjust the size as needed
    width: 300, // Adjust the size as needed
    borderRadius: 150, // Half of the height and width to make it a circle
    alignItems: 'center',
    justifyContent: 'center',
  },
  text: {
    textAlign: 'center',
    color: '#007AFF', // Replace with the actual text color
    fontSize: 24,
  },
});

export default SplashScreen;
