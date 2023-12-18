//
//  Progress.swift
//  newApp
//
//  Created by Varun Patel on 4/18/23.
//

import Foundation
import SwiftUI
struct Progress: View {
    var body: some View {
        VStack {
            List {
                Button("Quiz Summary (\(numQuestionsAnswered) answered)") {
                    //Check if user exists
                }
                .foregroundColor(.black)
                .frame(width: 300, height: 50)
                .background(Color.blue)
                .cornerRadius(10)
            }
        }.navigationTitle(Text("Progress").font(.system(size: 56)))
    }
}
