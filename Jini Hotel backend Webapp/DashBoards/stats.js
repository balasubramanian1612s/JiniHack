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
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  var database = firebase.database();  
  var ref = database.ref("Customer_stats")
 // var ts = Math.round((new Date()).getTime() / 1000);
/*
  var data = {
      userID : "somethingdsiaisdasd",
      hotel  : "biopeter",
      arrive : "epochTimeStamp",
      depart : "epochTimeStamp",
      members : "3",
      room    : "II",
      status : "InRoom"

  }

ref.push(data)


ref.on('value',gotData,errData);
*/

function gotData(data){
  var tableBody = document.getElementById("mains")
  console.log(tableBody,data.val())
  var stats = data.val();
  var keys = Object.keys(stats);
  for(var i = 0; i<keys.length;i++){
    var k =keys[i];
    var tr = document.createElement('TR');
    tableBody.appendChild(tr);
    var needed = [i+1,stats[k].arrive,stats[k].depart,stats[k].userID,stats[k].hotel,stats[k].members,stats[k].room,stats[k].status]
    for (var j=0; j<needed.length; j++){
          var td = document.createElement('TD');
          td.appendChild(document.createTextNode(needed[j]));
          tr.appendChild(td);
      }
    
  }
}

function errData(data){
  console.log(data);
}
