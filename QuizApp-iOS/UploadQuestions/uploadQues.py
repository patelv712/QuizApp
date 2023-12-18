import json
import collections
import pymongo
from pymongo import MongoClient

# Connect to the database
cluster = MongoClient('mongodb+srv://quizAppMobileUser:xkLaBBaa1dTsZuX8@quizappdb.7ltdg.mongodb.net/test')
# Selecting Database
db = cluster['Questions']
# Selecting Collections
collection = db["Data"]

def add_data():
    with open("textbook-v1.0-1.json") as json_file:
        data = json.load(json_file)
        data = data['data']
        for v in data:
            for value in v['paragraphs']:
                for value2 in value['qas']:
                    question = value2['question'].strip().lower().replace('"', "'")
                    title = v['title'].strip()
                    answer = 'unknown'
                    if value2['is_impossible'] == False:
                        answer = value2['answers'][0]['text']
                    print(question)
                    print(answer)
                    print(title)
                    query = {
                        'Question': question,
                        'Answer': answer,
                        'Chapter': title
                    }
                    collection.insert_one(query)



add_data()
