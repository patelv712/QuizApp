from monkeylearn import MonkeyLearn
import pandas as pd
from sqlalchemy import create_engine
import csv
import py_mysql_connector

engine = create_engine("mysql+mysqlconnector://root:password@localhost:3306/its")
query = pd.read_sql_query('SELECT content FROM eSPFirst WHERE chapter = 1', engine)
df = pd.DataFrame(query)
# print(df["content"].tolist())

ml = MonkeyLearn('e1b4b52e3301fd4bdbd4c4d3bca0ed071a428c20') #put in your own key
data = (df["content"].tolist())
model_id = 'ex_BaZh3m3R'
result = ml.extractors.extract(model_id, data)

filename = 'keywords.csv'
file = "extractions.csv"
# with open(filename, 'w', newline='') as f:
#     writer = csv.writer(f)
#     for item in result.body:
#         writer.writerow([item["text"], item["external_id"], item["error"], item["extractions"]])


with open(file, 'w', newline='') as f:
    writer = csv.writer(f)
    for item in result.body:
        for extraction in item["extractions"]:
            writer.writerow([extraction["tag_name"], extraction["parsed_value"], extraction["count"], extraction["forms"], extraction["relevance"]])
