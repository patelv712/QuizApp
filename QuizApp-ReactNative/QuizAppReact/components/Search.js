import React, { useState } from 'react';
import { View, TextInput, FlatList, TouchableOpacity, Text, StyleSheet, Switch } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import axios from 'axios';

const OPENAI_API_KEY = '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'; // Replace with your actual OpenAI API key

const SearchPage = ({ navigation }) => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [useChatGPT, setUseChatGPT] = useState(false);

  // Dummy data for example purposes
  const originalData = [
    { key: 'Creating New Software'},
    { key: 'Data Structures'},
    { key: 'ECE 2035 Test 1' },
    { key: 'ECE 2035 Test 2' },
    { key: 'Heap Management' },
    { key: 'Software performance & reliability' },
    { key: 'Supporting Data Abstraction' },
    { key: 'New final test' },
    { key: 'Wavelengths' }
  ];

  const toggleSearchMode = () => {
    const newUseChatGPTState = !useChatGPT;
    setUseChatGPT(newUseChatGPTState);

    // When toggling to local search, show original data; otherwise clear results
    if (!newUseChatGPTState) {
      setResults(originalData);
    } else {
      setResults([]);
    }
  };

  const handleSearch = async () => {
    
    if (useChatGPT) {
      // ChatGPT search logic
      try {
        const endpoint = 'https://api.openai.com/v1/engines/text-davinci-003/completions';
        const response = await axios.post(endpoint, {
          prompt: query,
          max_tokens: 1500,
        }, {
          headers: {
            'Authorization': `Bearer ${OPENAI_API_KEY}`,
          }
        });
        setResults([{ key: response.data.choices[0].text}]);
      } catch (error) {
        console.error("Error fetching data from OpenAI: ", error);
        setResults([{ key: 'Error in fetching response from ChatGPT'}]);
      }
    } else {
      // Local search logic
      const filteredData = query.trim()
        ? originalData.filter(item =>
            item.key.toLowerCase().includes(query.toLowerCase())
          )
        : originalData;
      setResults(filteredData);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.headerContainer}>
      <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.backButton}
      >
            <Icon name="arrow-back-circle" size={24} color="white" /> 
      </TouchableOpacity>
        <Text style={styles.title}>Search</Text>
        <View style={styles.toggleContainer}>
          <Text style={styles.toggleLabel}>{useChatGPT ? 'Smart Search On' : 'Smart Search Off'}</Text>
          <Switch
            trackColor={{ false: "#767577", true: "#81b0ff" }}
            thumbColor={useChatGPT ? "#f5dd4b" : "#f4f3f4"}
            ios_backgroundColor="#3e3e3e"
            onValueChange={toggleSearchMode}
            value={useChatGPT}
          />
        </View>
      </View>

      <TextInput
        style={styles.searchBar}
        placeholder="Search"
        value={query}
        onChangeText={text => {
          setQuery(text);
          if (!useChatGPT) {
            handleSearch(); // Call the search function on each text change
          }
        }}
        onSubmitEditing={handleSearch}
      />

      <FlatList
        data={results}
        renderItem={({ item }) => (
          <TouchableOpacity style={styles.listItem}>
            <Text style={styles.itemText}>{item.key}</Text>
          </TouchableOpacity>
        )}
        keyExtractor={(item, index) => index.toString()}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: 60,
  },
  backButton: {
    backgroundColor: '#000',
  },
  headerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    marginBottom: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  toggleContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  toggleLabel: {
    fontSize: 16,
    marginRight: 10,
  },
  searchBar: {
    height: 40,
    borderWidth: 1,
    borderColor: '#000',
    borderRadius: 10,
    paddingHorizontal: 10,
    marginHorizontal: 20,
    marginBottom: 20,
  },
  listItem: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  itemText: {
    fontSize: 18,
  },
});

export default SearchPage;
