//  Quizzes.swift
//  newApp
//
//  Created by Sanjit Pingili on 4/19/23.
//

import Foundation
import SwiftUI

public var num1 = Int.random(in: 1..<5140)
public var flag1 = false
struct Quizzes: View {
    @State private var selectedAnswerIndex: Int?
    //@State private var num = Int.random(in: 1..<5140)
    @State private var arr = [String]()

    func generateNewQuestion() {
        let correctAnswer = ques[num1].Answer
        var choices = [correctAnswer]
        while choices.count < 4 {
            let randomIndex = Int.random(in: 1..<5140)
            let randomAnswer = ques[randomIndex].Answer
            if !choices.contains(randomAnswer) {
                choices.append(randomAnswer)
            }
        }
        arr = choices.shuffled()
        selectedAnswerIndex = nil // reset the selected answer
    }

    func getButtonBackground(for index: Int) -> Color {
        if let selected = selectedAnswerIndex {
            if index == selected {
                if arr[selected] == ques[num1].Answer {
                    return Color.green
                } else {
                    return Color.red
                }
            }
        }
        return Color.blue
    }

    var body: some View {
        VStack{
            VStack {
                Text(ques[num1].Question)
                    .font(.headline)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.center)
                    .lineLimit(nil)
                    .bold()
                    .padding(.bottom, 50.0)
            }
            VStack {
                ForEach(arr.indices, id: \.self) { index in
                    Button(action: {
                        selectedAnswerIndex = index
                    }) {
                        Text(arr[index])
                            .foregroundColor(.black)
                            .frame(width: 300, height: 50)
                            .background(getButtonBackground(for: index))
                            .cornerRadius(10)
                            .padding(.vertical)
                    }
                }
            }
            HStack {
                Button("Save") {
                    flag1 = true;
                }
                .foregroundColor(.black)
                .frame(width: 100, height: 50)
                .background(Color.blue)
                .cornerRadius(10)
                .padding(.vertical)
                
                Button("Next") {
                    //goes to next question
                    numQuestionsAnswered += 1 // update number of questions answered
                    num1 = Int.random(in: 1..<5140) // generate new question
                    generateNewQuestion() // generate new set of options
                }
                .foregroundColor(.black)
                .frame(width: 100, height: 50)
                .background(Color.blue)
                .cornerRadius(10)
                .padding(.vertical)
            }
        }.navigationTitle(Text("Quizzes").font(.system(size: 56)))
        .onAppear {
            generateNewQuestion() // generate a new question when the view appears
        }
    }
}
