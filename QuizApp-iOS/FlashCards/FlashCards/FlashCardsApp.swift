//
//  FlashCardsApp.swift
//  FlashCards
//
//  Created by Neha Lalani on 10/31/22.
//

import SwiftUI

@main
struct FlashCardsApp: App {
    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
