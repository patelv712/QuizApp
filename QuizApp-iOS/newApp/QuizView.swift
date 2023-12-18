//  QuizView.swift
//  newApp
//
//  Created by Sanjit Pingili on 03/12/23.

import SwiftUI

let data = DataLoader().userData
public var num = Int.random(in: 1..<5140)
public var flag = false

struct QuizView: View {
    @State var frontText = data[num].Question
    @State var backText = data[num].Answer
    @State var flipped = false

    var body: some View {
        ZStack{
            Color.blue
            Circle()
                .scale(1.7)
                .foregroundColor(.white.opacity(0.2))
            

            VStack {
                Text("Flashcards")
                    .font(.largeTitle)
                    .bold()
                    .padding(.bottom, 500)
            }

            VStack {
                ZStack {
                    Text(frontText)
                        .opacity(flipped ? 0 : 1)
                        .rotation3DEffect(.degrees(flipped ? 180 : 0), axis: (x: 0, y: 1, z: 0))
                    Text(backText)
                        .opacity(flipped ? 1 : 0)
                        .rotation3DEffect(.degrees(flipped ? 0 : 180), axis: (x: 0, y: 1, z: 0))
                }
                .frame(height: 200)
                .frame(maxWidth: .infinity)
                .background(Color.white)
                .overlay(
                    Rectangle()
                        .stroke(Color.black, lineWidth: 4)
                )
                .padding()
                .onTapGesture {
                    withAnimation {
                        flipped.toggle()
                    }
                }
            }

            VStack {
                Spacer()
                HStack {
                    Button(action: {
                        num = Int.random(in: 1..<5140)
                        frontText = data[num].Question
                        backText = data[num].Answer
                        flipped = false
                    }, label: {
                        Text("Next")
                            .bold()
                            .foregroundColor(.black)
                            .padding()
                            .background(Color.white)
                            .cornerRadius(10)
                    })
                    Button(action: {
                               flag = true
                           }) {
                               Image(systemName: "star.fill")
                                   .foregroundColor(.yellow)
                                   .font(.system(size: 30))
                                   .padding(50)
                           }
                    
                }
                .padding(.bottom, 50)
            }
        }
    }
}

struct QuizView_Previews: PreviewProvider {
    static var previews: some View {
        QuizView()
    }
}
