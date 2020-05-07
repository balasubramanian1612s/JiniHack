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
import android.widget.EditText;
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

public class ValetService extends AppCompatActivity {

    Spinner hours,minutes,ampms;
    Button schedule;
    ArrayList<String> hr=new ArrayList<>();
    ArrayList<String> min=new ArrayList<>();
    ArrayList<String> ampm=new ArrayList<>();
    LinearLayout layout;
    TextView vehicleNoTv,tokenNumberTv;
    TextView info;
    EditText regnNo;
    EditText tokenNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valet_service);

        Intent intent=getIntent();
        final String hotelName=intent.getStringExtra("HotelName");

        hours=findViewById(R.id.hourSpinnerCar);
        tokenNumberTv=findViewById(R.id.tokenNumberTv);
        vehicleNoTv=findViewById(R.id.vehicleNoTv);
        minutes=findViewById(R.id.minSpinnerCar);
        ampms=findViewById(R.id.ampmSpinnerCar);
        schedule=findViewById(R.id.callValetBtn);
        layout=findViewById(R.id.mainLayout);
        info=findViewById(R.id.infoEt);
        regnNo=findViewById(R.id.regnNoEt);
        tokenNo=findViewById(R.id.tokenNoEt);


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

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("valetServices").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications").child("Services").child("valetServices").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        if (snapshot.getValue().toString().equals("Accepted")) {
                            regnNo.setVisibility(View.INVISIBLE);
                            tokenNo.setVisibility(View.INVISIBLE);
                            vehicleNoTv.setVisibility(View.INVISIBLE);
                            tokenNumberTv.setVisibility(View.INVISIBLE);
                            schedule.setVisibility(View.INVISIBLE);
                            info.setText("You've already approached the valet");
                            layout.setVisibility(View.INVISIBLE);
                            schedule.setEnabled(false);
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
                String regn=regnNo.getText().toString();
                String token=tokenNo.getText().toString();
                String time="ASAP";
                if(!hour.equals("Hours")&&!mins.equals("Minutes")&&!ampmsel.equals("am/pm")&&regn!=null&&token!=null){

                    Log.i("Time is:", hour + ":" + mins + " " + ampmsel);
                    time = hour + ":" + mins + " " + ampmsel;

                    ref.child("regNo").setValue(regn);
                    ref.child("tokenNo").setValue(token);
                    ref.child("Time").setValue(time);
                    ref.child("userName").setValue("username");
                    ref.child("Response").setValue("Not Accepted");

                    userRef.child("regNo").setValue(regn);
                    userRef.child("tokenNo").setValue(token);
                    userRef.child("Time").setValue(time);
                    userRef.child("userName").setValue("username");
                    userRef.child("Response").setValue("Not Accepted");

                    regnNo.setVisibility(View.INVISIBLE);
                    tokenNo.setVisibility(View.INVISIBLE);
                    info.setText("Successfully approached the valet");
                    layout.setVisibility(View.INVISIBLE);
                    schedule.setEnabled(false);



                }else if(hour.equals("Hours")&&mins.equals("Minutes")&&ampmsel.equals("am/pm")&&regn!=null&&token!=null){
                    ref.child("regNo").setValue(regn);
                    ref.child("tokenNo").setValue(token);
                    ref.child("Time").setValue(time);
                    ref.child("userName").setValue("username");

                    userRef.child("regNo").setValue(regn);
                    userRef.child("tokenNo").setValue(token);
                    userRef.child("Time").setValue(time);
                    userRef.child("userName").setValue("username");

                    regnNo.setVisibility(View.INVISIBLE);
                    tokenNo.setVisibility(View.INVISIBLE);
                    info.setText("Successfully approached the valet");
                    layout.setVisibility(View.INVISIBLE);
                    schedule.setEnabled(false);
                }

                }
        });
    }
}
