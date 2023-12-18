//
//  SplashScreenView.swift
//  newApp
//
//  Created by Varun Patel on 3/2/23.
//

import SwiftUI

struct SplashScreenView: View {
    @State private var isActive = false
    var body: some View {
        ZStack{
            Color.blue
                .ignoresSafeArea()
            Circle()
                .scale(1.7)
                .foregroundColor(.white.opacity(0.4))
            Circle()
                .scale(1.35)
                .foregroundColor(.white.opacity(0.4))
            Circle()
                .scale(1)
                .foregroundColor(.white)
            if (isActive) {
                LogIn()
            } else {
                VStack {
                    Text("Welcome to QuizApp")
                        .font(.title)
                        .bold()
                        .padding(.bottom, 2.0)
                }
                .onAppear{
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                        self.isActive = true
                    }
                }
            }
        }
    }
}

struct SplashScreenView_Previews: PreviewProvider {
    static var previews: some View {
        SplashScreenView()
    }
}
