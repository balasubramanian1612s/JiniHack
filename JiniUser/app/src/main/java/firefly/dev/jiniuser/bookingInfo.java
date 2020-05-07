package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firefly.dev.jiniuser.emailstuff.GMailSender;

public class bookingInfo extends AppCompatActivity {

    TimePicker timePicker;
    TextView textView;
    EditText noOfDays;
    Button nextBooking;
    FirebaseDatabase database;
    DatabaseReference reference, reffAuth;
    EditText fromDate;
    String newline=System.getProperty("line.separator");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        fromDate=findViewById(R.id.fromDate);
        noOfDays=findViewById(R.id.noOfDays);
        nextBooking=findViewById(R.id.nextBooking);
        textView=findViewById(R.id.textView);
        database=FirebaseDatabase.getInstance();
        final Intent intent=getIntent();
        final String roomName=intent.getStringExtra("RoomName");
        final String hotelName=intent.getStringExtra("HotelName");
        reference=database.getReference().child("Hotels").child(hotelName).child("Bookings");
        reffAuth=database.getReference().child("User").child(FirebaseAuth.getInstance().getUid());


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:
                        startActivity(new Intent(getApplicationContext(),all_hotels.class));
                        overridePendingTransition(0,0);


                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(),bookings.class));
                        overridePendingTransition(0,0);



                }


                return false;
            }
        });
        nextBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fromDate.getText().toString().matches("\\d{2}-\\d{2}-\\d{4}")){


                    String uidr=reference.push().getKey();
                    reference=reference.child(uidr);
                    reference.child("BookDate").setValue(fromDate.getText().toString());
                    reference.child("TimeIn").setValue(getCurrentTime());
                    reference.child("StayDays").setValue(noOfDays.getText().toString());
                    reffAuth=reffAuth.child("My Bookings");
                    String uifauth=reffAuth.push().getKey();
                    reffAuth=reffAuth.child(uifauth);
                    reffAuth.child("BookDate").setValue(fromDate.getText().toString());
                    reffAuth.child("TimeIn").setValue(getCurrentTime());
                    reffAuth.child("StayDays").setValue(noOfDays.getText().toString());

                    Intent intent1=new Intent(bookingInfo.this,bookingPage.class);
                    intent1.putExtra("RoomName",roomName);
                    intent1.putExtra("HotelName",hotelName);
                    intent1.putExtra("uidr",uidr);
                    intent1.putExtra("uidauth",uifauth);
                    startActivity(intent1);

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);

                    GMailSender sender = new GMailSender("developers.firefly@gmail.com", "WeAreFireflyDevelopers");
                    try {
                        sender.sendMail("Booking!", "Hi there"+newline+"We Are From FireFly Developers!"+newline+"New Order Arrival!"+"Booking Id: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+newline+"Customer Name: "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName() +newline+"Room Booked: "+roomName+newline+"Date Of Booking"+fromDate.getText().toString()+ newline+"Check In Time:"+getCurrentTime()+newline+"Stay Days:noOfDays.getText().toString()" ,"developers.firefly@gmail.com","companyofbala@gmail.com");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }





                }
                else {

                    textView.setVisibility(View.VISIBLE);
                }




            }
        });




    }

    public String getCurrentTime(){
        String currentTime=timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
        return currentTime;
    }

}
