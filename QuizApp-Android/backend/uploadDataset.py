import json
import string

f = open("textbook-v1.0.json")

jsonFile = json.load(f)
formattedData = []


# loop through each element in the data part of the json file
for element in jsonFile["data"]:
    # extract chapter and section from title
    title = element["title"]
    for char in title:
        if char in string.digits:
            chapter = int(char)
            break
    section = int(title[-1])
    paragraphs = element["paragraphs"]
    for paragraph in paragraphs:
        for questionAnswer in paragraph["qas"]:
            # extract question and answers for each section
            question = questionAnswer["question"]
            # if the question does not have an answer, skip it
            if questionAnswer["answers"] == []:
                continue
            answer = questionAnswer["answers"][0]["text"]
            res = {"question": question, "answer": answer, "chapter": chapter, "section": section}
            formattedData.append(res)

# create a json file with the data
with open("formattedData.json", "w") as o:
    json.dump(formattedData, o)