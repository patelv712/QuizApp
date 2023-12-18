//
//  SavedQuestions.swift
//  newApp
//
//  Created by Varun Patel on 4/18/23.
//  Updated by Sahana Krishnan on 4/19/23

import Foundation
import SwiftUI

struct SavedQuestions: View {
    @State private var flag = false
    @State private var flag1 = false
    @State private var flag2 = false
    @State private var flag3 = false
    var body: some View {
        NavigationView {
            VStack {
                List {
                    Button("Saved Questions") {
                        flag = true;
                    }
                    .foregroundColor(.black)
                    .frame(width: 300, height: 50)
                    .background(Color.blue)
                    .cornerRadius(10)

                    NavigationLink(destination: SavedView(), isActive: $flag) {
                        EmptyView()
                        
                    }

                    Button("Incorrect Questions") {
                        flag1 = true;
                    }
                    .foregroundColor(.black)
                    .frame(width: 300, height: 50)
                    .background(Color.blue)
                    .cornerRadius(10)
                    NavigationLink(destination: IncorrectView(), isActive: $flag1) {
                        EmptyView()
                        
                    }
                }
            }
            .navigationTitle(Text("Saved Questions").font(.system(size: 56)))
        }
    }
}
struct SavedView: View {
    @State private var flag2 = false
    @State private var flag3 = false
    var body: some View {
        VStack {
            Text("Your saved questions")
                .font(.headline)
                .padding(.top, 20)
            Button("Study Mode Saved questions") {
                flag2 = true;
            }
            .foregroundColor(.black)
            .frame(width: 300, height: 50)
            .background(Color.blue)
            .cornerRadius(10)

            NavigationLink(destination: SavedView1(), isActive: $flag2) {
                EmptyView()
                
            }
            Text("")
            Text("")
            Text("")
            Text("")
            Text("")
            
            Button("Quiz Mode Saved questions") {
                flag3 = true;
            }
            .foregroundColor(.black)
            .frame(width: 300, height: 50)
            .background(Color.blue)
            .cornerRadius(10)

            NavigationLink(destination: SavedView2(), isActive: $flag3) {
                EmptyView()
                
            }
        }
        .navigationBarTitle("Saved", displayMode: .inline)
    }
}

struct IncorrectView: View {
    @State private var flag2 = false
    @State private var flag3 = false
    var body: some View {
        VStack {
            Text("Your incorrect questions")
                .font(.headline)
                .padding(.top, 20)
            Button("Study Mode incorrect questions") {
                flag2 = true;
            }
            .foregroundColor(.black)
            .frame(width: 300, height: 50)
            .background(Color.blue)
            .cornerRadius(10)

            NavigationLink(destination: SavedView3(), isActive: $flag2) {
                EmptyView()
                
            }
            Text("")
            Text("")
            Text("")
            Text("")
            Text("")
            
            Button("Quiz Mode incorrect questions") {
                flag3 = true;
            }
            .foregroundColor(.black)
            .frame(width: 300, height: 50)
            .background(Color.blue)
            .cornerRadius(10)

            NavigationLink(destination: SavedView4(), isActive: $flag3) {
                EmptyView()
                
            }
        }
        .navigationBarTitle("Incorrect", displayMode: .inline)
    }
}


struct SavedView1: View {
    var body: some View {
        VStack {
            Text("Your Study Mode Saved questions")
                .font(.headline)
                .foregroundColor(.blue)
                .padding(.top, 20)
            
            Spacer()
            
            let data = DataLoader().userData
            //var num = Int.random(in: 1..<5140)
            if (flag) {
                @State var frontText = data[num].Question
                
                Text(frontText)
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.blue, lineWidth: 2)
                    )
                    .foregroundColor(.blue)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .multilineTextAlignment(.center)
            }
        }
        .navigationBarTitle("Saved", displayMode: .inline)
        .background(Color.white)
    }
}



struct SavedView2: View {
    var body: some View {
        VStack {
            Text("Your Quiz Mode Saved questions")
                .font(.headline)
                .foregroundColor(.blue)
                .padding(.top, 20)
            
            Spacer()
            if (flag1) {
                let data = DataLoader().userData
                //var num1 = Int.random(in: 1..<5140)
                @State var frontText = data[num1].Question
                
                Text(frontText)
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.blue, lineWidth: 2)
                    )
                    .foregroundColor(.blue)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .multilineTextAlignment(.center)
            }
        }
            .navigationBarTitle("Saved", displayMode: .inline)
            .background(Color.white)
    }
}

struct SavedView3: View {
    var body: some View {
        VStack {
            Text("Your Study Mode Incorrect questions")
                .font(.headline)
                .padding(.top, 20)
            // add your saved questions list here
        }
        .navigationBarTitle("Saved", displayMode: .inline)
    }
}

struct SavedView4: View {
    var body: some View {
        VStack {
            Text("Your Quiz Mode Incorrect questions")
                .font(.headline)
                .padding(.top, 20)
            // add your saved questions list here
        }
        .navigationBarTitle("Saved", displayMode: .inline)
    }
}
