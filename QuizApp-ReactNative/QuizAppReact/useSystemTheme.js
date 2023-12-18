// useSystemTheme.js
import { useColorScheme } from 'react-native';

const useSystemTheme = () => {
  const systemTheme = useColorScheme();
  return systemTheme; // 'dark' or 'light'
};

export default useSystemTheme;
