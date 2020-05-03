const $tableID = $('#table');
 const $BTN = $('#export-btn');
 const $EXPORT = $('#export');

 const newTr = `
<tr class="hide">
  <td class="pt-3-half" contenteditable="true" id="`+istr+`">Example</td>
  <td class="pt-3-half" contenteditable="true">Example</td>
  <td class="pt-3-half" contenteditable="true">Example</td>
  <td class="pt-3-half" contenteditable="true">Example</td>
  <!--td class="pt-3-half">
    <span class="table-up"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-up" aria-hidden="true"></i></a></span>
    <span class="table-down"><a href="#!" class="indigo-text"><i class="fas fa-long-arrow-alt-down" aria-hidden="true"></i></a></span>
  </td-->
  <td>
  <span class="table-success"><button type="button"
      class="btn btn-success btn-rounded btn-sm my-0">Change</button></span>
  </td>
  <td>
    <span class="table-remove"><button type="button" class="btn btn-danger btn-rounded btn-sm my-0 waves-effect waves-light">Remove</button></span>
  </td>
</tr>`;

 $('.table-add').on('click', 'i', () => {

 a=$('tbody').append(newTr);
 var lis=a.find('td')
 var stats = database.ref("Hotels/"+id+"/Bookings/"+keys[parseInt(lis[0].id.charAt(0))])
 var paste = ["BookDate","StayDays","Username","RoomType"]
 var fin ={};
  for(var i=0;i<4;i+=1){
    temp=lis[i].innerText
    fin[paste[i]] =temp ;
    console.log(paste[i],lis[i].innerHTML)
  }
  console.log("create:",fin)
   //$tableID.find('table').append($clone);
  stats.set(fin)
 });


 $tableID.on('click', '.table-remove', function () {
   a=$(this).parents('tr');
   lis=a.find('td');
   var stats = database.ref("Hotels/"+id+"/Bookings/"+keys[parseInt(lis[0].id.charAt(0))])
   stats.remove();
   a.detach();
 });

 $tableID.on('click', '.table-success', function () {
  a=$(this).parents('tr');
  lis=a.find('td');
  console.log(keys[parseInt(lis[0].id.charAt(0))])
  var stats = database.ref("Hotels/"+id+"/Bookings/"+keys[parseInt(lis[0].id.charAt(0))])
  var paste = ["BookDate","StayDays","Username","RoomType"]
  var fin ={};
  for(var i=0;i<4;i+=1){
    temp=lis[i].innerHTML
    fin[paste[i]] =temp ;
    console.log(paste[i],lis[i].innerHTML)
  }
  console.log("hello",fin)
  stats.update(fin);

});

 // A few jQuery helpers for exporting only
 jQuery.fn.pop = [].pop;
 jQuery.fn.shift = [].shift;

 $BTN.on('click', () => {

   const $rows = $tableID.find('tr:not(:hidden)');
   const headers = [];
   const data = [];

   // Get the headers (add special header logic here)
   $($rows.shift()).find('th:not(:empty)').each(function () {

     headers.push($(this).text().toLowerCase());
   });

   // Turn all existing rows into a loopable array
   $rows.each(function () {
     const $td = $(this).find('td');
     const h = {};

     // Use the headers from earlier to name our hash keys
     headers.forEach((header, i) => {

       h[header] = $td.eq(i).text();
     });

     data.push(h);
   });

   // Output the result
   $EXPORT.text(JSON.stringify(data));
 });