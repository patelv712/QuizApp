#just remove tag, not based on layer or tage logic
def removeTag(text):
    i=0
    j=0
    while j<len(text):
        if(text[j]=='<'):
            i=j
        elif(text[j]=='>'):
            text = text[:i]+' '+text[j+1:]
            j=i-1
        j=j+1
    return text

def removeExtraBlank(text):
    i=0
    j=0
    while j<len(text):
        if(text[j].isspace()):
            i=j
            while(j<len(text) and text[j].isspace()):
                j=j+1
            if(i+1!=j):
                text = text[:i]+' '+text[j:]
                j=i
        j=j+1
    text = text.strip()
    return text

def simpleTextCleaner(text):
    return removeExtraBlank(removeTag(text))
