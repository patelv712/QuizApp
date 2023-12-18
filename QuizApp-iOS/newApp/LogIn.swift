//
//  ContentView.swift
//  newApp
//
//  Created by Neha Lalani on 9/27/22.
//

import SwiftUI
let ques = DataLoader().userData
var numQuestionsAnswered = 0

struct GlobalVariables {
    static var flagArr = [Int]()
}

struct LogIn: View {
    @State private var username = ""
    @State private var password = ""
    @State private var wrongUsername = 0
    @State private var wrongPassword = 0
    @State private var showingLoginScreen = false
    @State private var goRegister = false

    var body: some View {
        NavigationView {
            ZStack {
                Color.blue
                    .ignoresSafeArea()
                Circle()
                    .scale(1.7)
                    .foregroundColor(.white.opacity(0.2))
                Circle()
                    .scale(1.35)
                    .foregroundColor(.white.opacity(0.6))
                Circle()
                    .scale(1)
                    .foregroundColor(.white)
                VStack {
                    Text("Welcome to QuizApp")
                        .font(.title)
                        .bold()
                        .padding(.bottom, 2.0)
                    
                    TextField("Username", text: $username)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(wrongUsername))
                    TextField("Password", text: $password)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(wrongPassword))
                    Button("Login") {
                        //Check if user exists
                        checkUser(username: username, password: password)
                    }
                    .foregroundColor(.white)
                    .frame(width: 300, height: 50)
                    .background(Color.gray)
                    .cornerRadius(10)
                    .padding(.bottom, 3.0)
                    
                    NavigationLink(destination: Home1(), isActive: $showingLoginScreen) {
                        EmptyView()
                    }
                    Button("Register") {
                        goRegister = true
                        //Check if user exists
                      
                    }
                    .foregroundColor(.white)
                    .frame(width: 300, height: 50)
                    .background(Color.gray)
                    .cornerRadius(10)
                    
                    NavigationLink(destination: Register(), isActive: $goRegister) {
                        EmptyView()
                    }
                }
            }
            .navigationBarHidden(true)
        }
    }
    func checkUser(username: String, password: String) {
        //add check for usernames
        if username.lowercased() == "test" {
            wrongUsername = 0
            if password.lowercased() == "password" {
                wrongPassword = 0
                showingLoginScreen = true
            } else {
                wrongPassword = 2
            }
        }
        if username.lowercased() == "vip" {
            wrongUsername = 0
            if password.lowercased() == "sahana" {
                wrongPassword = 0
                showingLoginScreen = true
            } else {
                wrongPassword = 2
            }
        }
        
        else {
            wrongUsername = 2
        }
    }
        

}
struct Register: View {
    @State private var firstName = ""
    @State private var lastName = ""
    @State private var email = ""
    @State private var username = ""
    @State private var password = ""
    @State private var reenteredPassword = ""
    @State private var invalidFirstname = 0
    @State private var invalidLastname = 0
    @State private var invalidEmail = 0
    @State private var invalidUsername = 0
    @State private var invalidPassword = 0
    @State private var invalidPassword2 = 0
    @State private var showingRegisterScreen = false
    @State private var showAlert = false
    var body: some View {
        NavigationView {
            ZStack {
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
                VStack {
                    Text("Register for TutorBot")
                        .font(.title)
                        .bold()
                        .padding(.bottom, 2.0)
                    TextField("First Name", text: $firstName)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidFirstname))
                    TextField("Last Name", text: $lastName)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidLastname))
                    TextField("Email", text: $email)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidEmail))
                    TextField("Username", text: $username)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidUsername))
                    TextField("Password", text: $password)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidPassword))
                    TextField("Re-enter Password", text: $reenteredPassword)
                        .padding()
                        .frame(width: 300, height: 50)
                        .background(Color.black.opacity(0.05))
                        .cornerRadius(10)
                        .border(.red, width: CGFloat(invalidPassword2))
                    Button("Create Account") {
                      
                        //Check if user exists
                        checkNewUser(username: username, password: password, reenteredPassword: reenteredPassword)
                    }
                    .alert("Passwords Don't Match", isPresented: $showAlert) {
                        Button("OK", role: .cancel) { }
                    } message: {
                        Text("Passwords must match")
                    }
                    .foregroundColor(.white)
                    .frame(width: 300, height: 50)
                    .background(Color.blue)
                    .cornerRadius(10)
                    
                    NavigationLink(destination: Home(), isActive: $showingRegisterScreen) {
                        EmptyView()
                    }
            
                }
            }
            .navigationBarHidden(true)
        }
    }
    
    func checkNewUser(username: String, password: String,
                      reenteredPassword : String) {
        //add check for usernames
        if username.count >= 5 {
            invalidUsername = 0
            if password.count >= 8 {
                invalidPassword = 0
                if reenteredPassword == password {
                    invalidPassword2 = 0;
                    showingRegisterScreen = true
                } else {
                    showAlert = true
                      
                    // Create new Alert
                    /**var dialogMessage = UIAlertController(title: "Passwords Don't Match", message: "Make sure passwords match", preferredStyle: .alert)
                  
                    let ok = UIAlertAction(title: "Cancel", style: .cancel, handler: { (action) -> Void in
                         print("Cancel button tapped")
                        })
                    dialogMessage.addAction(ok)*/
                  
                
                    invalidPassword2 = 2
                }
            } else {
                invalidPassword = 2
            }
        } else {
            invalidUsername = 2
        }
    }
        

}

struct Home1: View {
    @State private var goFlashcards = false
    @State private var goQuizzes = false
    @State private var goProgress = false
    @State private var goSettings = false
    @State private var goSaved = false
    @State private var selectedTab = 0
    @State private var showPersonalInfoSheet = false
    
    var body: some View {
            //NavigationView {
                VStack{
                
                    VStack {
                        Color.blue.ignoresSafeArea()
                        Circle()
                            .scale(1.7)
                            .foregroundColor(.white.opacity(0.4))
                        Circle()
                            .scale(1.35)
                            .foregroundColor(.white.opacity(0.4))
                        Circle()
                            .scale(1)
                            .foregroundColor(.white)
                        Button("Info") {
                            showPersonalInfoSheet = true
                            Image(systemName: "person.crop.circle")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .padding()
                                .frame(width: 100, height: 100, alignment: .topTrailing)
                                .offset(x: 100, y: 0)
                        }
                        .offset(x: 100, y: 0)

                        Text("Menu")
                            .font(.largeTitle)
                            .fontWeight(.bold)
                            .multilineTextAlignment(.center)
                            .lineLimit(nil)
                            .bold()
                            .padding()
                    }
                    .sheet(isPresented: $showPersonalInfoSheet) {
                        VStack() {
                            HStack() {
                                Spacer()
                                VStack() {
                                    Image(systemName: "person.circle.fill")
                                        .resizable()
                                        .frame(width:150, height:150)
                                        .clipShape(Circle())
                                        .padding(.top, 44)
                                    
                                    Text("John Smith").font(.system(size: 40).bold())
                                        .padding(.top, 12)
                                    Text("@jsmith80").font(.system(size: 20).bold())
                                        .foregroundColor(.gray)
                                        .padding(.top, 12)
                                    
                                }
                               
                                Spacer()
                                
                            }
                            VStack() {
//                                Image(systemName: "phone.fill")
//                                    .foregroundColor(.gray)
//                                    .frame(width:50, height:50)
//
                                Text("--------------------------------").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                
                                Label("                 123-456-7890", systemImage: "phone.fill")
                                    .foregroundColor(.gray)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Label("   jsmith80@gatech.edu", systemImage: "envelope").foregroundColor(.gray)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Text("").font(.system(size: 20).bold())
                                    .foregroundColor(.gray)
                                    .padding(.top, 12)
                                Label("   Password", systemImage: "eye.fill").foregroundColor(.gray)
                                
                            }
                            Spacer()
                        }
                    }

                    VStack() {
                        
                        VStack() {
                            Button("Settings") {
                                //Check if user exists
                                goSettings = true
                            }
                            .foregroundColor(.black)
                            .frame(width: 180, height: 80)
                            .background(Color.orange)
                            .cornerRadius(10)
                            .padding()
                            
                            NavigationLink(destination: Settings(), isActive: $goSettings) {
                                EmptyView()
                            }
                        }
                        VStack(){
                            Button("Flashcards") {
                                //Check if user exists
                                goFlashcards = true
                            }
                            .foregroundColor(.black)
                            .frame(width: 180, height: 80)
                            .background(Color.blue)
                            .cornerRadius(10)
                            .padding()
                            
                            NavigationLink(destination: Home(), isActive: $goFlashcards) {
                                EmptyView()
                            }
                        }
                        VStack(){
                            Button("Quizzes") {
                                //Check if user exists
                                goQuizzes = true
                            }
                            .foregroundColor(.black)
                            .frame(width: 180, height: 80)
                            .background(Color.red)
                            .cornerRadius(10)
                            .padding()
                            
                            NavigationLink(destination: Quizzes(), isActive: $goQuizzes) {
                                EmptyView()
                            }
                        }
                        VStack(){
                            Button("Saved Questions") {
                                //Check if user exists
                                goSaved = true
                            }
                            .foregroundColor(.black)
                            .frame(width: 180, height: 80)
                            .background(Color.yellow)
                            .cornerRadius(10)
                            .padding()
                            
                            NavigationLink(destination: SavedQuestions(), isActive: $goSaved) {
                                EmptyView()
                            }
                        }
                        VStack(){
                            Button("Progress") {
                                //Check if user exists
                                goProgress = true
                            }
                            .foregroundColor(.black)
                            .frame(width: 180, height: 80)
                            .background(Color.pink)
                            .cornerRadius(10)
                            .padding()
                            
                            NavigationLink(destination: Progress(), isActive: $goProgress) {
                                EmptyView()
                            }
                        }
                    }
                }
            //}
        }
}

struct Home: View {
    @State private var goFlashcards = false
    @State private var goQuizzes = false
    @State private var goProgress = false
    @State private var goSettings = false
    @State private var goSaved = false
    @State private var selectedTab = 0
    
    var body: some View {
        TabView(selection: $selectedTab) {
            QuizView()
                .onTapGesture {
                    selectedTab = 0
                }
                .tabItem() {
                    Image(systemName: "doc.fill")
                    Text("Flashcards")
                }
            Quizzes()
                .onTapGesture {
                    selectedTab = 1
                }
                .tabItem() {
                    Image(systemName: "chart.bar.doc.horizontal.fill")
                    Text("Quizzes")
                }
            SavedQuestions()
                .onTapGesture {
                    selectedTab = 2
                }
                .tabItem() {
                    Image(systemName: "square.and.arrow.down.on.square.fill")
                    Text("Saved Questions")
                }
            Progress()
                .onTapGesture {
                    selectedTab = 3
                }
                .tabItem() {
                    Image(systemName: "chart.bar.xaxis")
                    Text("Progress")
                }
            Settings()
                .onTapGesture {
                    selectedTab = 4
                }
                .tabItem() {
                    Image(systemName: "gearshape.fill")
                    Text("Settings")
                }
        }
    }
}
    struct Flashcards: View {
        var body: some View {
            //NavigationView {
                ZStack {
                    Rectangle()
                        .scale(0.3)
                }
            //}
        }
    }


    


    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            LogIn()
        }
    }



//new flash cards implementation

struct CardFront: View {
    @Binding var degree : Double
    let textContext : String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20).stroke(.green.opacity(0.5), lineWidth: 10).padding()
            
            RoundedRectangle(cornerRadius: 20).stroke(.green.opacity(0.5), lineWidth: 10).padding()
            
            VStack {
                Text("Question:")
//                Text("New question 1")
                
                Text (textContext)
                    .lineLimit(10)
//                Text("answer here")
                
            }
        } .rotation3DEffect(Angle(degrees: degree), axis: (x: 0.0, y: 1.0, z: 0.0))
    }
}





struct CardBack: View {
    @Binding var degree : Double
    let textContext : String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20).stroke(.green.opacity(0.5), lineWidth: 10).padding()
            
            RoundedRectangle(cornerRadius: 20).stroke(.green.opacity(0.5), lineWidth: 10).padding()
            
            VStack {
//                Text("Question:")
//                Text("New question 1")
                
                Text (textContext)
                    .lineLimit(10)
                //Text("answer here")
                
            }
        } .rotation3DEffect(Angle(degrees: degree), axis: (x: 0.0, y: 1.0, z: 0.0))
    }
}
