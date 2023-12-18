import React, { useState, useContext } from 'react';
import { View, Text, Switch, TouchableOpacity, StyleSheet, SafeAreaView } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import DarkModeContext from '../DarkModeContext'; 

const SettingsScreen = ({ navigation }) => {
  const { isDarkMode, toggleDarkMode } = useContext(DarkModeContext);
  const [isPrivacyEnabled, setIsPrivacyEnabled] = useState(false);

  const backgroundStyle = {
    backgroundColor: isDarkMode ? '#000' : '#FFF',
  };
  const textStyle = {
    color: isDarkMode ? '#FFF' : '#000',
  };
  const togglePrivacy = () => setIsPrivacyEnabled(previousState => !previousState);

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <Text style={styles.title}>Settings</Text>

        <TouchableOpacity
        onPress={() => navigation.goBack()}
        style={styles.backButton}
        >
          <Icon name="arrow-back-circle" size={24} color="white" /> 
        </TouchableOpacity>

        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Change Username</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Change Password</Text>
        </TouchableOpacity>

        <View style={[styles.switchContainer, backgroundStyle]}>
          <Text style={[styles.switchText, textStyle]}>Dark Mode</Text>
          <Switch
            trackColor={{ false: "#767577", true: "#81b0ff" }}
            thumbColor={isDarkMode ? "#f5dd4b" : "#f4f3f4"}
            ios_backgroundColor="#3e3e3e"
            onValueChange={toggleDarkMode}
            value={isDarkMode}
          />
        </View>

        <View style={styles.switchContainer}>
          <Text style={styles.switchText}>Privacy</Text>
          <Switch
            trackColor={{ false: "#767577", true: "#81b0ff" }}
            thumbColor={isPrivacyEnabled ? "#f5dd4b" : "#f4f3f4"}
            onValueChange={togglePrivacy}
            value={isPrivacyEnabled}
          />
        </View>

        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>App Version</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button}
          onPress={() => navigation.navigate('AboutScreen')}>
          <Text style={styles.buttonText}>About</Text> 
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#007AFF', // Blue background color to match your theme
  },
  container: {
    flex: 1,
    padding: 20,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#FFFFFF', // Assuming a white title to contrast the background
    marginBottom: 20,
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
  button: {
    backgroundColor: '#FFFFFF', // White background for buttons
    padding: 15,
    marginVertical: 10,
    borderRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 3,
  },
  buttonText: {
    color: '#007AFF', // Blue text color to match the button titles with the theme
    fontSize: 18,
  },
  switchContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    backgroundColor: '#FFFFFF', // White background for switch container
    paddingVertical: 15,
    paddingHorizontal: 10,
    marginVertical: 10,
    borderRadius: 10,
    alignItems: 'center',
    elevation: 3,
  },
  switchText: {
    color: '#007AFF', // Blue text color for the switch labels
    fontSize: 18,
  },
});

export default SettingsScreen;
