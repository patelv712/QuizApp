import math

class spacedRepetitionObject():
    def __init__(self, questionAnswerObject):
        self.questionAnswerObject = questionAnswerObject
        self.repetitions = 0
        self.easeFactor = 2.5
        self.interval = 0
    def calculate(self, quality):
        interval = 0
        if quality >= 3:
            if self.repetitions == 0:
                interval = 1
            elif self.repetitions == 1:
                interval = 6
            else:
                interval = math.ceil(self.interval * self.easeFactor)
            self.interval = interval
            self.repetitions += 1
            self.easeFactor += (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))
        if quality < 3 and quality > 1.3:
            self.repetitions = 0
            self.interval = 1
        self.easeFactor = min(self.easeFactor, 1.3)
        return self.interval
    def decrement(self):
        self.interval -= 1