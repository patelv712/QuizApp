//
//  newAppApp.swift
//  newApp
//
//  Created by Neha Lalani on 9/27/22.
//

import SwiftUI
import Firebase

@main
struct newAppApp: App {
    init() {
        FirebaseApp.configure()
    }
    var body: some Scene {
        WindowGroup {
            SplashScreenView()
        }
    }
}
