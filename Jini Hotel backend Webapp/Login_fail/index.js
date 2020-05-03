
var firebaseConfig = {
    apiKey: "AIzaSyDmsLpmW-YujiaE1i2PzOZIn4NAJVgmZuU",
    authDomain: "bookjini-firefly.firebaseapp.com",
    databaseURL: "https://bookjini-firefly.firebaseio.com",
    projectId: "bookjini-firefly",
    storageBucket: "bookjini-firefly.appspot.com",
    messagingSenderId: "637317936718",
    appId: "1:637317936718:web:30f9fad2e42d3b114b351f",
    measurementId: "G-TWT9XSG0T1"
  };
  
firebase.initializeApp(firebaseConfig);

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      // User is signed in.
  
      var user = firebase.auth().currentUser;
  
      if(user != null){
        window.alert("DONE : ");
        var email_id = user.email;
        var email_verified=user.emailVerified;
        console.log(email_id,email_verified)
        window.location="DashBoards/manage.html"
  
      }
  
    } else {
      // No user is signed in.
  
      console.log("opsie")
  
    }
  });
  
  function login(){
  
    var userEmail = document.getElementById("email_field").value;
    var userPass = document.getElementById("password_field").value;

    console.log(userEmail,userPass)
  
    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
      // Handle Errors here.
      var errorCode = error.code;
      var errorMessage = error.message;
  
      console.log("failed")
      // ...
    });
  
  }
  
  function create(){
    
    var userEmail = document.getElementById("email_field").value;
    var userPass = document.getElementById("password_field").value;
  
    firebase.auth().createUserWithEmailAndPassword(userEmail, userPass).catch(function(error) {
      // Handle Errors here.
      var errorCode = error.code;
      var errorMessage = error.message;
      // ...
  
      window.alert("Error : " + errorMessage);
    });
  
  }
  
  function send_verification(){var user = firebase.auth().currentUser;
  
    user.sendEmailVerification().then(function() {
      // Email sent.
      window.alert("Verification Sent! Please Check Your Email.")
  
    }).catch(function(error) {
      // An error happened.
      window.alert("Error -" + error.message);
    });
  }
  
  
  function logout(){
    firebase.auth().signOut();
  }
  