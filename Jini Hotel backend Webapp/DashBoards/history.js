console.log("Getting firebase...")
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
      StayDays : "epochTimeStamp",
      members : "3",
      room    : "II",
      status : "InRoom"

  }

ref.push(data)
*/

ref.on('value',gotData,errData);


function gotData(data){
  var tableBody = document.getElementById("mains")
  var stats = data.val();
  var keys = Object.keys(stats);
  for(var i = 0; i<keys.length;i++){
    var k =keys[i];
    var tr = document.createElement('TR');
    tableBody.appendChild(tr);
    var needed = [i+1,stats[k].arrive,stats[k].StayDays,stats[k].userID,stats[k].hotel,stats[k].members,stats[k].room,stats[k].status]
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



function convertTimestamp(timestamp) {
  var d = new Date(timestamp * 1000),	// Convert the passed timestamp to milliseconds
		yyyy = d.getFullYear(),
		mm = ('0' + (d.getMonth() + 1)).slice(-2),	// Months are zero based. Add leading 0.
		dd = ('0' + d.getDate()).slice(-2),			// Add leading 0.
		hh = d.getHours(),
		h = hh,
		min = ('0' + d.getMinutes()).slice(-2),		// Add leading 0.
		ampm = 'AM',
		time;
			
	if (hh > 12) {
		h = hh - 12;
		ampm = 'PM';
	} else if (hh === 12) {
		h = 12;
		ampm = 'PM';
	} else if (hh == 0) {
		h = 12;
	}
	
	// ie: 2013-02-18, 8:35 AM	
	time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;
		
	return time;
}


function hist(){

}