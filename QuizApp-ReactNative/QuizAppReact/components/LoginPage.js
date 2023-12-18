import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, Text, StyleSheet, Alert } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { CommonActions } from '@react-navigation/native';

const LoginPage = ({ navigation }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [logins, setLogins] = useState({});

  const handleLogin = () => {
    if (logins[username] && logins[username] === password) {
      // After login success, reset the navigation state to only include HomeScreen
      Alert.alert('Login Success', 'Welcome back!', [
        {
          text: "OK",
          onPress: () => {
            navigation.navigate("MenuScreen");
          }
        }
      ]);
    } else {
      Alert.alert('Login Failed', 'The username or password is incorrect.');
    }
  };

  const handleRegister = () => {
    if (username.trim() && password.trim()) {
      setLogins({...logins, [username]: password});
      Alert.alert('Login Added', `The login for ${username} has been added.`);
      // Clear the input fields
      setUsername('');
      setPassword('');
    } else {
      Alert.alert('Input Error', 'Please enter both a username and a password.');
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.loginBox}>
        <Text style={styles.title}>Welcome to QuizApp</Text>
        <TextInput
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          style={styles.input}
        />
        <TextInput
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
          style={styles.input}
        />
        <TouchableOpacity onPress={handleLogin} style={styles.button}>
          <Text style={styles.buttonText}>Login</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={handleRegister} style={styles.button}>
          <Text style={styles.buttonText}>Register</Text>
        </TouchableOpacity>
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
  loginBox: {
    width: '80%',
    backgroundColor: '#FFF',
    borderRadius: 10,
    padding: 20,
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 10, // For Android shadow
    shadowColor: '#000', // For iOS shadow
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    width: '100%',
    borderRadius: 5,
    padding: 10,
    marginBottom: 15,
  },
  button: {
    backgroundColor: 'gray',
    padding: 10,
    borderRadius: 5,
    width: '100%',
    alignItems: 'center',
    marginBottom: 10,
  },
  buttonText: {
    color: 'white',
    fontSize: 18,
  },
  addButton: {
    marginTop: 10,
    backgroundColor: '#28a745', // Different color for the add button
  },
});

export default LoginPage;