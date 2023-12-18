import json
import sqlite3

# Load the JSON data from a file
with open('textbook-v1.0-1.json') as f:
    data = json.load(f)

# Create a new SQLite database
conn = sqlite3.connect('questions.db')

# Create a table to store the questions
create_table_query = """
CREATE TABLE questions (
    id INTEGER PRIMARY KEY,
    question TEXT,
    answer TEXT,
    chapter TEXT
)
"""
conn.execute(create_table_query)

# Insert the data into the SQLite database
for paragraph in data['data']:
    chapter_title = paragraph['title']
    for qa in paragraph['paragraphs'][0]['qas']:
        question = qa['question'].strip().lower().replace('"', "'")
        answer = 'unknown'
        if not qa['is_impossible']:
            answer = qa['answers'][0]['text']
        insert_query = f"""
        INSERT INTO questions (question, answer, chapter) VALUES (
            "{question}",
            "{answer}",
            "{chapter_title}"
        )
        """
        conn.execute(insert_query)

# Commit the changes to the database and close the connection
conn.commit()
conn.close()
