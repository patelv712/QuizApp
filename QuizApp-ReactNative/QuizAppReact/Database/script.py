import json

def generate_sql_inserts(json_filepath, output_sql_filepath):
    with open(json_filepath, 'r') as file:
        textbook_data = json.load(file)
    
    data_section = textbook_data['data']
    insert_statements = []

    topic_id = 1
    paragraph_id = 1
    answer_id = 1

    for topic in data_section:
        # Insert statement for the topic
        topic_title = topic.get('title', '').replace("'", "''")
        subject = 'ECE'  
        insert_statements.append(f"INSERT INTO Topics (TopicID, Title, Subject) VALUES ({topic_id}, '{topic_title}', '{subject}');")

        for paragraph in topic.get('paragraphs', []):
            # Insert statement for the paragraph
            paragraph_content = paragraph.get('context', '').replace("'", "''")
            insert_statements.append(f"INSERT INTO Paragraphs (ParagraphID, TopicID, Content) VALUES ({paragraph_id}, {topic_id}, '{paragraph_content}');")

            for qna in paragraph.get('qas', []):
                # Insert statement for the question
                question_id = qna.get('id', '').replace("'", "''")
                question_text = qna.get('question', '').replace("'", "''")
                is_impossible = qna.get('is_impossible', False)
                insert_statements.append(f"INSERT INTO Questions (QuestionID, ParagraphID, QuestionText, IsImpossible) VALUES ('{question_id}', {paragraph_id}, '{question_text}', {is_impossible});")

                for answer in qna.get('answers', []):
                    # Insert statement for the answer
                    answer_text = answer.get('text', '').replace("'", "''")
                    start_position = answer.get('answer_start', 0)
                    end_position = answer.get('answer_end', 0)
                    insert_statements.append(f"INSERT INTO Answers (AnswerID, QuestionID, AnswerText, StartPosition, EndPosition) VALUES ({answer_id}, '{question_id}', '{answer_text}', {start_position}, {end_position});")
                    answer_id += 1

            paragraph_id += 1

        topic_id += 1

    # Writing the insert statements to a file
    with open(output_sql_filepath, 'w') as output_file:
        for statement in insert_statements:
            output_file.write(statement + "\n")

# Path to your JSON file
json_filepath = 'textbook-v1.0-1.json'

# Output SQL file path
output_sql_filepath = 'insert_statements.sql'

# Generate the SQL file
generate_sql_inserts(json_filepath, output_sql_filepath)
