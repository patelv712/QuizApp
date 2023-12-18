import json
from srObject import spacedRepetitionObject

f = open("formattedData.json")
jsonFile = json.load(f)
srObjectList = []
for element in jsonFile[0:10]:
    srObjectList.append(spacedRepetitionObject(element))

dayCount = 1
questionsRemaining = 0
totalQuestions = 0
while True:
    totalQuestions = 0
    print("Welcome to Day " + str(dayCount) + "!")
    if (dayCount != 1):
        for element in srObjectList:
            element.decrement()
            if element.interval == 0:
                totalQuestions += 1
    else:
        questionsCompleted = 0
        totalQuestions = 10
    questionsRemaining = totalQuestions
    questionsCompleted = 0
    while questionsRemaining > 0:
        count = 1
        for element in srObjectList:
            if (element.interval == 0):
                print("Questions completed: " + str(questionsCompleted) + "/" + str(totalQuestions))
                print("Questions remaining: "+ str(questionsRemaining) + "/" + str(totalQuestions))
                print("Question " + str(count) + ": " + element.questionAnswerObject["question"])
                flip = input("Flip? (y/n): ")
                print("Answer: " + element.questionAnswerObject["answer"])
                quality = 5 - int(input("Rate the difficulty of the question (0-5): "))
                newInterval = element.calculate(quality)
                if newInterval != 0:
                    questionsCompleted += 1
                    questionsRemaining -= 1
            count += 1
    dayCount += 1
    print("You have completed this session's quiz!")
