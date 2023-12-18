// DarkModeContext.js
import React from 'react';

const DarkModeContext = React.createContext({
  isDarkMode: false,
  toggleDarkMode: () => {},
});

export default DarkModeContext;
