firebase.auth().onAuthStateChanged(function(user) {
  if (user) {
    // User is signed in.

    document.getElementById("user_div").style.display = "block";
    document.getElementById("login_div").style.display = "none";

    var user = firebase.auth().currentUser;

    if(user != null){

      var email_id = user.email;
      var email_verified=user.emailVerified;
      window.location="DashBoards/index.html"
     // document.getElementById("user_para").innerHTML = "Welcome " + email_id +
      //                                                 "Verified : " + email_verified

    }

  } else {
    // No user is signed in.

    document.getElementById("user_div").style.display = "none";
    document.getElementById("login_div").style.display = "block";

  }
});

function login(){

  var userEmail = document.getElementById("email_field").value;
  var userPass = document.getElementById("password_field").value;

  firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
    // Handle Errors here.
    var errorCode = error.code;
    var errorMessage = error.message;

    window.alert("Error : " + errorMessage);

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
