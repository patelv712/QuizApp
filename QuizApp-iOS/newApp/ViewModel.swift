//
//  ViewModel.swift
//  newApp
//
//  Created by Zhen Hong Tan on 3/1/23.
//

import Foundation
import Firebase

import Foundation
import Firebase

class ViewModel: ObservableObject {
    
    @Published var list = [firebaseDB]()
    
    
    func getData() {
        print("here")
        // Get a reference to the database
        let db = Firestore.firestore()
        
        // Read the documents at a specific path
        db.collection("UserData").getDocuments { snapshot, error in
            
            // Check for errors
            if error == nil {
                // No errors
                
                if let snapshot = snapshot {
                    
                    // Update the list property in the main thread
                    DispatchQueue.main.async {
                        
                        // Get all the documents and create Todos
                        self.list = snapshot.documents.map { d in
                            
                            // Create a Todo item for each document returned
                            return firebaseDB(id: d.documentID,
                                                                   UserName: d["name"] as? String ?? "",
                                                                   Password: d["notes"] as? String ?? "")
                        }
                    }
                    
                    
                }
            }
            else {
                // Handle the error
            }
        }
    }
    
}
