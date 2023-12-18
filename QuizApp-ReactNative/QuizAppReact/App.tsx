import React, { useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LoginPage from './components/LoginPage'; // Replace with the actual path to your LoginPage file
import MenuScreen from './components/Menu'; // Replace with the actual path to your HomeScreen file
import AboutScreen from './components/AboutPage'
import SearchScreen from './components/Search'
import SettingsScreen from './components/Settings'
import FlashScreen from './components/FlashCard'
import DarkModeContext from './DarkModeContext';
import { enableScreens } from 'react-native-screens';

enableScreens();


const Stack = createNativeStackNavigator();

const App = () => {
  const [isDarkMode, setIsDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
  };
  return (
    <DarkModeContext.Provider value={{ isDarkMode, toggleDarkMode }}>
        <NavigationContainer>
          <Stack.Navigator initialRouteName="Login" screenOptions={{headerShown: false}}>
          <Stack.Screen name="Login" component={LoginPage} />
          <Stack.Screen name="MenuScreen" component={MenuScreen} />
          <Stack.Screen name="AboutScreen" component={AboutScreen} />
          <Stack.Screen name="SearchScreen" component={SearchScreen} />
          <Stack.Screen name="SettingsScreen" component={SettingsScreen} />
          <Stack.Screen name="FlashScreen" component={FlashScreen} />
        </Stack.Navigator>
      </NavigationContainer>
    </DarkModeContext.Provider>


  );
};


export default App;
