import sys

from example import *

localtext = [""]
indexes = [0]
choosen = [""]

factor = 0.8;

q = 'We want to create a new vector called <B><TT>zz</TT></B> that when played though the speaker with <B><TT>soundsc(zz,8000)</TT></B> will be two sequential tones with the <B>FIRST tone being LOWER in frequency'
additionalkw = [["A-to-D",10],["soundsc",1.0],["speaker",1]]

def init():
    global localtext
    localtext = chapter1content()    
    kws = kwsfilter(q);
    for w in additionalkw:
        kws.append(w);
    setChoosen(calculatePharagraph(kws, localtext), localtext.split("."))
    localtext = localtext.split(".")

def setChoosen(value, sentences):
    max = 0;
    for i in range(0, len(value)):
        if(value[max]<value[i]):
            max = i
    global factor
    max = value[max]*factor
    global choosen
    choosen = []
    global indexes
    indexes = []
    for i in range(0, len(value)):
        if(value[i]>=max):
            choosen.append(sentences[i])
            indexes.append(i)


init()

while(1):
    inx = input("cmd:")
    if(inx=='i'):
        x = int(input("id:"))
        if(x>=0 and x<len(choosen)):
            print("index {}:{}".format(indexes[x], choosen[x]))
        else:
            print("Out Of Bound")
    if(inx=='t'):
        x = int(input("s:"))
        y = int(input("e:"))
        for a in range(x,y):
            if(a>=0 and a<len(localtext)):
                print("index {}:{}".format(a, localtext[a]))
            else:
                print("Out Of Bound")
                break
    if(inx=='l'):
        print(len(choosen))
    if(inx=='q'):
        break;
