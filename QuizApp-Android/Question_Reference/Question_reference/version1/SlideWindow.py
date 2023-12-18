import math

beforeword = [" ","\n"];
afterword = [" ", ".","\n"];
windowsize=[3,7,11]
windowWeight=[1,0.4,0.05]

def calculatePharagraph(keywordsAndWeight, text):
    sentences = text.split(".")
    #print(sentences)
    weights = [0]*len(sentences)
    resultL = [0]*len(sentences)
    resultR = [0]*len(sentences)
    window = [0]*len(windowsize)
    for i in range(len(sentences)):
        #print(i);
        #print("%d: %s" % (i,sentences[i]))
        weights[i]=calculateSentence(keywordsAndWeight, sentences[i])
        
    resultL = calculatePharagraphL(weights);
    weights.reverse()
    resultR = calculatePharagraphL(weights);
    resultR.reverse()
    weights.reverse()
    for i in range(len(resultL)):
        resultL[i]+=resultR[i]-weights[i]*windowWeight[0]
    return resultL

def calculatePharagraphL(weights):
    result = [0]*len(weights)
    window = 0
    size = math.floor((1+windowsize[0])/2)
    off = 0
    for j in range(len(windowsize)):
        window=0
        if(j!=0):
            size = math.floor((windowsize[j]-windowsize[j-1])/2)
            off = math.floor((1+windowsize[j-1])/2)
        for i in range(len(weights)):
            window+=weights[i]
            if(i>=size):
                window-=weights[i-size]
            if(i<len(weights)-off):
                result[i+off]+=window*windowWeight[j]
    return result

def calculateSentence(keywordsAndWeight, sentence):
    cur = 0
    weight = 0
    for i in range(len(keywordsAndWeight)):
        word = keywordsAndWeight[i][0];
        cur=0
        while cur!=-1:
            cur = sentence.find(word,cur)
            if cur!=-1:
                #print((cur<=0 or (sentence[cur-1] in beforeword)) and (cur>=len(sentence)-len(word) or (sentence[cur+len(word)] in afterword)))
                #print(cur)
                #print(word)
                #print(len(word))
                #print(len(sentence))
                if (cur<=0 or (sentence[cur-1] in beforeword)) and (cur>=len(sentence)-len(word) or (sentence[cur+len(word)] in afterword)):
                    weight+=keywordsAndWeight[i][1]
                cur = cur + 1
    return weight

"""kw=[["research",1],["algorithms",2],["search",1]]"""
"""se=""""""Contextual search refers to proactively capturing the information need of a user by automatically augmenting the user query with information extracted from the search context; for example, by using terms from the web page the user is currently browsing or a file the user is currently editing.
We present three different algorithms to implement contextual search for the Web.
The first, query rewriting (QR), augments each query with appropriate terms from the search context and uses an off-the-shelf web search engine to
answer this augmented query. The second, rank-biasing (RB),
generates a representation of the context
and answers queries using a custom-built search engine that exploits this representation.
The third, iterative filtering meta-search (IFM),
generates multiple subqueries based on the user query and appropriate terms from the search context,
uses an off-the-shelf search engine to answer these subqueries, and re-ranks the results of the subqueries using rank aggregation methods."""

"""
#print(calculateSentence(kw, se))
res = calculatePharagraph(kw, se)
max = 0
index = 0
for i in range(len(res)):
    if res[i]>res[index]:
        index = i
print(res)
print(index)
print(se.split(".")[index])"""
