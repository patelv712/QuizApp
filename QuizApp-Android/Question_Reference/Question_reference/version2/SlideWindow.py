import math

class properties:
    beforeword = [" ","\n","-","\""];
    afterword = [" ", ".","\n","-","\"",","];
    windowsize=[3,7,11]
    windowWeight=[1,0.4,0.05]

class text:
    content = ""
    chapter = -1
    weight = 0
    def __init__(self, content:str, chapter:int):
        self.content = content
        self.chapter = chapter

    def setWeight(self, w:float):
        if(w<0):
            return
        self.weight = w

    def __str__(self):
        return "[{} : {}]".format(self.content, self.weight)

    def splitByComma(te:str):
        res = []
        te = te.split(".")
        for t in te:
            if(len(t)>0):
                temp = text(t+".", -1)
                temp.setWeight(-1)
                res.append(temp)
        return res
    def textArrayStr(texts:['text']):
        p = ""
        for i in range(len(t)):
            p = p + t[i].__str__() +','
        return p
        

class keywords:
    length = 0
    words = []
    weights = []
    def __init__(self, words:[str], weights:[float]):
        if(len(words)!=len(weights)):
            print("keywords and weight has different length")
        else:
            self.words = words
            self.weights = weights
            self.length = len(words)

def calculatePharagraph(keywordsAndWeight:'keywords', text:['text']):
    #print(sentences)
    weights = [0]*len(text)
    resultL = [0]*len(text)
    resultR = [0]*len(text)
    for i in range(len(text)):
        weights[i]=calculateSentence(keywordsAndWeight, text[i])
        
    resultL = calculatePharagraphL(weights);
    weights.reverse()
    resultR = calculatePharagraphL(weights);
    resultR.reverse()
    weights.reverse()
    for i in range(len(resultL)):
        resultL[i]+=resultR[i]-weights[i]*properties.windowWeight[0]
        text[i].setWeight(resultL[i])
    return text

def calculatePharagraphL(weights:[float]):
    result = [0]*len(weights)
    windowsize = properties.windowsize
    windowWeight = properties.windowWeight
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

def calculateSentence(keywords:'keywords', text:'text'):
    cur = 0
    weight = 0
    sentence = text.content
    words = keywords.words
    weights = keywords.weights
    for i in range(keywords.length):
        word = words[i]
        cur=0
        while cur!=-1:
            cur = sentence.find(word,cur)
            if cur!=-1:
                if (cur<=0 or (sentence[cur-1] in properties.beforeword)) and\
                   (cur>=len(sentence)-len(word) or (sentence[cur+len(word)] in properties.afterword)):
                    weight+=weights[i]
                cur = cur + 1
    return weight
"""
w = ["a", "b", "c", "hello", "about", "world"]
we = [1, 3.2, 5.1, 0.1, 10, 20]
k = keywords(w, we);
te = "a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a.a."
t = text.splitByComma(te)
calculatePharagraph(k, t)
print(text.textArrayStr(t))
"""
