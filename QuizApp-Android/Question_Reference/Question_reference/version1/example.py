#need to change the USER_NAME and PASSWORD for sql connection first



import pandas as pd                         #import these and download them on command line
from sqlalchemy import create_engine        #import these and download them on command line
import csv
from SlideWindow import *
from mySimpleFilter import simpleTextCleaner

dbUserInfo = "root:password"

#this function will recure the raw text(with html tags) of chapter one as a single string
def chapter1content():
    engine = create_engine("mysql+mysqlconnector://"+dbUserInfo+"@localhost:3306/its") #create_engine("mysql+mysqlconnector://username:password:host:port_number:database_name")
    query = pd.read_sql_query('SELECT * FROM eSPFirst where chapter=3 and meta=\'paragraph\'', engine) #put desired query to convert
    df = pd.DataFrame(query) #convert query into python data structure
    #print(df)

    text_list = df["content"].tolist() # convert into a series (the column containing content)
    #print(text_list)
    text = ""
    for te in text_list:
        text = text+" "+te
    #print(text)
    return simpleTextCleaner(text)

def chapterContent(number):
    engine = create_engine("mysql+mysqlconnector://"+dbUserInfo+"@localhost:3306/its") #create_engine("mysql+mysqlconnector://username:password:host:port_number:database_name")
    query = pd.read_sql_query('SELECT * FROM eSPFirst where chapter='+number+' and meta=\'paragraph\'', engine) #put desired query to convert
    df = pd.DataFrame(query) #convert query into python data structure
    #print(df)

    text_list = df["content"].tolist() # convert into a series (the column containing content)
    #print(text_list)
    text = ""
    for te in text_list:
        text = text+" "+te
    #print(text)
    return simpleTextCleaner(text)

#this function will read the extractions.csv from '../keyword_search/extractions.csv', and return a list of [<keyword>,<weight>].
#Now, this function uses a simplest why to delete duplicate.
def kwsFromExtractions():
    kw = []
    w = []
    with open('../../keyword_search/extractions.csv',mode='r') as csv_file:
        csv_reader = csv.reader(csv_file)
        line_count = 0
        for row in csv_reader:
            if(row[1] not in w):
                kw.append([row[1],float(row[4])])           #a very simple duplicate filter
                w.append(row[1])
            line_count += 1
    line_count = 0
    for row in kw:
        line_count += 1
    #print(f'Processed {line_count} lines.')
    return kw

#this function means to find all matching keywords from keyword list, kwsFromExtractions(), within a question, and return the keyword-weight pair.
def kwsfilter(question):
    kw = kwsFromExtractions()
    kw_in = []
    for w in kw:
        if(w[0] in question):
            kw_in.append(w)
    kw = kwFromIndex()
    for w in kw:
        if(w[0] in question):
            kw_in.append(w)
    return kw_in

#this function will read the extractions.csv from '../keyword_search/extractions.csv', and return a list of [<keyword>,<weight>].
#Now, this function uses a simplest why to delete duplicate.
def kwsampleQkw():
    kw = []
    w = []
    with open('../../keyword_search/question_extractions.csv',mode='r') as csv_file:
        csv_reader = csv.reader(csv_file)
        line_count = 0
        for row in csv_reader:
            if(row[1] not in w):
                kw.append([row[1],float(row[4])])          #a very simple duplicate filter
                w.append(row[1])
            line_count += 1
    line_count = 0
    for row in kw:
        line_count += 1
    #print(f'Processed {line_count} lines.')
    return kw

def kwFromIndex():
    engine = create_engine("mysql+mysqlconnector://"+dbUserInfo+"@localhost:3306/its") #create_engine("mysql+mysqlconnector://username:password:host:port_number:database_name")
    query = pd.read_sql_query('SELECT * FROM index_1 ', engine) #put desired query to convert
    df = pd.DataFrame(query) #convert query into python data structure
    #print(df)

    text_list = df["name"].tolist() # convert into a series (the column containing content)
    kw = []
    w = []
    for i in range(0, len(text_list)):
        if("(" in text_list[i]):
            a = text_list[i].index("(")
            b = text_list[i].index(")")
            t = [simpleTextCleaner(text_list[i][a+1:b]),5.0]
            if t[0] not in w:
                w.append(t[0])
                kw.append(t)
            t = [simpleTextCleaner(text_list[i][0:a]+text_list[i][b+1:]),5.0]
            if t[0] not in w:
                w.append(t[0])
                kw.append(t)
        else:
            t = [simpleTextCleaner(text_list[i]),5.0]
            if t[0] not in w:
                w.append(t[0])
                kw.append(t)
    print(kw)
    return kw

def runExample(q):
    return runExampleC(q, "1")

def runExampleC(q, chapter):
    text = chapterContent(chapter)
    #q = 'And what is the difference between a time signal and an acoustic signal?'
    additionalkw = []
    print(q)


    print("\n")
    print("************************************************************************")
    print("*************************Chapter 1 Content******************************")
    print("************************************************************************")
    print(text)

    print("\n")
    print("************************************************************************")
    print("************** Keywords also in extraction.csv *************************")
    print("************************************************************************")

    kws = kwsfilter(q);
    for w in additionalkw:
        kws.append(w);
    print(kws)
    res=calculatePharagraph(kws,text)

    max = 0
    index = 0
    for i in range(len(res)):
        if res[i]>res[index]:
            index = i

    print("\n")
    print("************************************************************************")
    print("************************Sentence_Weight_Res*****************************")
    print("************************************************************************")
    #print(res)
    print("The index of the max weight: {}".format(index))

    sentences = text.split(".")
    i = 0

    print("\n")
    print("************************************************************************")
    print("**********Sample_Result,including 3 sentence before and after***********")
    print("************************************************************************")
    returnText = "RESULT: "
    for s in sentences:
        if(i<index+3 and i>index-3):
            print("\nIndex:{}".format(i))
            print(s)
            returnText = returnText+'<br><br>'+s
        i=i+1
    return returnText

#runExample("")
