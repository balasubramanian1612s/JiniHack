package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LaundryService extends AppCompatActivity {

    Spinner hours,minutes,ampms;
    Button schedule;
    ArrayList<String> hr=new ArrayList<>();
    ArrayList<String> min=new ArrayList<>();
    ArrayList<String> ampm=new ArrayList<>();
    LinearLayout layout;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry_service);

        Intent intent=getIntent();
        final String hotelName=intent.getStringExtra("HotelName");

        hours=findViewById(R.id.hourSpinner);
        minutes=findViewById(R.id.minSpinner);
        ampms=findViewById(R.id.ampmSpinner);
        schedule=findViewById(R.id.scheduleBtn);
        layout=findViewById(R.id.main);
        info=findViewById(R.id.textViewinfo);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:


                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(),bookings.class));
                        overridePendingTransition(0,0);



                }


                return false;
            }
        });



        hr.add("Hours");
        min.add("Minutes");
        ampm.add("am/pm");

        for(int i=1;i<=12;i++){
            hr.add(i+"");
        }
        min.add(0+"");
        min.add(30+"");
        ampm.add("am");
        ampm.add("pm");

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, hr);
        hours.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        hours.setAdapter(adp);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, min);
        minutes.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        minutes.setAdapter(adp1);

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, ampm);
        ampms.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ampms.setAdapter(adp2);

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("laundryServices").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications").child("Services").child("laundryServices").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getValue().toString().equals("Accepted")) {
                            layout.setVisibility(View.INVISIBLE);
                            schedule.setEnabled(false);
                            schedule.setVisibility(View.INVISIBLE);
                            info.setText("You've already booked an appointment !");
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour=hours.getSelectedItem().toString();
                String mins=minutes.getSelectedItem().toString();
                String ampmsel=ampms.getSelectedItem().toString();
                if(!hour.equals("Hours")&&!mins.equals("Minutes")&&!ampmsel.equals("am/pm")) {

                    Log.i("Time is:", hour + ":" + mins + " " + ampmsel);
                    String time = hour + ":" + mins + " " + ampmsel;

                    ref.child("Response").setValue("Not Accepted");
                    ref.child("RoomNo").setValue("123");
                    ref.child("Time").setValue(time);
                    ref.child("userName").setValue("username");

                    userRef.child("Response").setValue("Not Accepted");
                    userRef.child("RoomNo").setValue("123");
                    userRef.child("Time").setValue(time);
                    userRef.child("userName").setValue("username");

                    layout.setVisibility(View.INVISIBLE);
                    schedule.setEnabled(false);
                    info.setText("You've successfully booked an appointment !");
                }
            }
        });
    }
}
