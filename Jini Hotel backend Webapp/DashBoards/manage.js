console.log("Getting firebase...")
var keys="";
var istr=0;
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
  var id="Farfield";
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  var database = firebase.database();  
  var ref = database.ref("Hotels/"+id+"/Bookings")

ref.on('value',gotData,errData);


function gotData(data){
  var tableBody = document.getElementById("mains")
  tableBody.innerHTML=""
  var stats = data.val();
  keys = Object.keys(stats);
  for(istr = 0; istr<keys.length;istr++){
    var k =keys[istr];
    var tr = document.createElement('TR');
    tr.id=istr.toString()
    tableBody.appendChild(tr);
    var needed = [stats[k].BookDate,stats[k].StayDays,stats[k].Username,stats[k].RoomType]
    for (var j=0; j<needed.length; j++){
          var td = document.createElement('TD');
          td.id = istr.toString()+"r"+ j.toString();
          td.Class="pt-3-half";
          td.contentEditable = "true";
          td.appendChild(document.createTextNode(needed[j]));
          tr.appendChild(td);
      }
      var td = document.createElement('TD');
          td.id = istr.toString()+"r"+ j.toString()
          td.contenteditable="true"
          td.class="pt-3-half"
      tr.appendChild(td)
      document.getElementById(td.id).innerHTML=`<span class="table-success"><button type="button"
      class="btn btn-success btn-rounded btn-sm my-0">Change</button></span>`;
      j+=1;
      var td = document.createElement('TD');
          td.id = istr.toString()+"r"+ j.toString()
          td.contenteditable="true"
          td.class="pt-3-half"
      tr.appendChild(td)
      document.getElementById(td.id).innerHTML=`<span class="table-remove"><button type="button"
      class="btn btn-danger btn-rounded btn-sm my-0">Remove</button></span>`;
    
  }
}

function errData(data){
  console.log(data);
}