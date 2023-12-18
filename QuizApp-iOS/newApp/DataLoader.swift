//
//  DataLoader.swift
//  newApp
//
//  Created by Zhen Hong Tan on 11/6/22.
//

import Foundation
import Firebase
public class DataLoader {
    @Published var userData = [UserData]()
    @Published var list = [firebaseDB]()

    
    init() {
        load()
        sort()
        removeUnknown()
        check()
        getData()
        
    }
    
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
                            print(d["UserName"])
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
    
    func load() {
        if let fileLocation = Bundle.main.url(forResource: "Data", withExtension: "json") {
            do {
                let data = try Data(contentsOf: fileLocation)
                let jsonDecoder = JSONDecoder()
                let dataFromJson = try jsonDecoder.decode([UserData].self, from: data)
                
                self.userData = dataFromJson
            } catch {

                print(error)
            }
        }
    }
    
    func sort() {
        self.userData = self.userData.sorted(by: { $0.Chapter < $1.Chapter})
    }
    
    func removeUnknown() {
        var num = 0
        var bck = self.userData.count - 1
        while(num < self.userData.count && num < bck) {
            if(userData[num].Answer == "unknown") {
                let st = userData[num]
                userData[num] = userData[bck]
                userData[bck] = st
                bck -= 1
            } else {
                num += 1
            }
        }
        print(num)
    }
    
    func check() {
        var num = 0
        while(num < 5140) {
           // print(userData[num].Answer)
            num += 1
        }
    }
    
}
