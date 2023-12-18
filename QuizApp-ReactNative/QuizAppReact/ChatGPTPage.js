import React, { useState } from 'react';
import { View, TextInput, Button, Text, ScrollView, StyleSheet } from 'react-native';
import axios from 'axios';

const ChatGPTPage = () => {
    const [inputText, setInputText] = useState('');
    const [response, setResponse] = useState('');

    const sendQuery = async () => {
        if (!inputText.trim()) {
            alert('Please enter a query');
            return;
        }

        try {
            const endpoint = 'https://api.openai.com/v1/engines/text-davinci-003/completions';

            const apiResponse = await axios.post(endpoint, {
                prompt: inputText,
                max_tokens: 1500, // Set to the desired maximum number of tokens
            }, {
                headers: {
                    'Authorization': `Bearer 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`, // Replace with your actual API key
                }
            });

            setResponse(apiResponse.data.choices[0].text);
        } catch (error) {
            console.error(error);
            setResponse('Error in fetching response');
        }
    };

    return (
        <View style={styles.container}>
            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    onChangeText={text => setInputText(text)}
                    value={inputText}
                    placeholder="Type your query here"
                />
                <Button
                    title="Send"
                    onPress={sendQuery}
                />
            </View>
            <ScrollView style={styles.responseContainer}>
                <Text style={styles.responseText}>
                    {response}
                </Text>
            </ScrollView>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 50,
        backgroundColor: '#fff',
    },
    inputContainer: {
        paddingHorizontal: 20,
        paddingBottom: 20,
    },
    input: {
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        marginBottom: 10,
    },
    responseContainer: {
        flex: 1,
        paddingHorizontal: 20,
    },
    responseText: {
        fontSize: 16,
    }
});

export default ChatGPTPage;


