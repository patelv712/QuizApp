//
//  Settings.swift
//  newApp
//
//  Created by Varun Patel on 4/18/23.
//

import Foundation
import SwiftUI
struct Settings: View {
    @Environment(\.colorScheme) var colorScheme
    @State private var goChangeUsername = false
    @State private var goChangePassword = false
    @State private var goAbout = false
    @State private var goAppVersion = false
    @State private var goPrivacy = false
    
    var body: some View {
        NavigationView {
            VStack {
                List {
                    NavigationLink(destination: ChangeUsernameView(), isActive: $goChangeUsername) {
                                Button("Change Username") {
                                    //Check if user exists
                                    goChangeUsername = true
                                }
                                .foregroundColor(.black)
                                .frame(width: 300, height: 50)
                                .background(Color.blue)
                                .cornerRadius(10)
                            }
                            
                            NavigationLink(destination: ChangePasswordView(), isActive: $goChangePassword) {
                                Button("Change Password") {
                                    //Check if user exists
                                    goChangePassword = true
                                }
                                .foregroundColor(.black)
                                .frame(width: 300, height: 50)
                                .background(Color.blue)
                                .cornerRadius(10)
                            }


                    Toggle(isOn: Binding<Bool>(
                            get: { self.colorScheme == .dark },
                            set: { _ in
                                if colorScheme == .light {
                                    UIApplication.shared.windows.first?.overrideUserInterfaceStyle = .dark
                                } else {
                                    UIApplication.shared.windows.first?.overrideUserInterfaceStyle = .light
                                }
                            }
                        )
                    ) {
                        Text("Dark Mode")
                    }
                    .foregroundColor(.black)
                    .frame(width: 300, height: 50)
                    .background(Color.blue)
                    .cornerRadius(10)

                    
                    NavigationLink(destination: Text("Your data is private"), isActive: $goPrivacy) {
                        Button("Privacy") {
                            goPrivacy = true
                        }
                        .foregroundColor(.black)
                        .frame(width: 300, height: 50)
                        .background(Color.blue)
                        .cornerRadius(10)
                    }
                    
                    NavigationLink(destination: Text("Version 1.0").background(Color.blue), isActive: $goAppVersion) {
                        Button("App Version") {
                            goAppVersion = true
                        }
                        .foregroundColor(.black)
                        .frame(width: 300, height: 50)
                        .background(Color.blue)
                        .cornerRadius(10)
                    }

                    NavigationLink(destination: Text("This is QuizApp, an application that was started in 2022. It allows students to take quizzes on subjects they need help with. Student can also access flashcards to help them study."), isActive: $goAbout) {
                        Button("About") {
                            goAbout = true
                        }
                        .foregroundColor(.black)
                        .frame(width: 300, height: 50)
                        .background(Color.blue)
                        .cornerRadius(10)
                    }
                }
            }.navigationTitle(Text("Settings").font(.system(size: 56)))
        }
    }
}
struct ChangeUsernameView: View {
    @State private var newUsername = ""

    var body: some View {
        VStack {
            TextField("New Username", text: $newUsername)
                .padding()
            
            Button("Save") {
                // Save the new username
            }
            .foregroundColor(.white)
            .padding()
            .background(Color.blue)
            .cornerRadius(10)
        }
        .padding()
        .navigationBarTitle("Change Username")
    }
}

struct ChangePasswordView: View {
    @State private var currentPassword = ""
    @State private var newPassword = ""

    var body: some View {
        VStack {
            SecureField("Current Password", text: $currentPassword)
                .padding()
            SecureField("New Password", text: $newPassword)
                .padding()
            
            Button("Save") {
                // Save the new password
            }
            .foregroundColor(.white)
            .padding()
            .background(Color.blue)
            .cornerRadius(10)
        }
        .padding()
        .navigationBarTitle("Change Password")
    }
}
