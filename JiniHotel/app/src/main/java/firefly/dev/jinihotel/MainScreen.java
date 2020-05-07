package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainScreen extends AppCompatActivity {

    CardView manageRooms;
    public static String mainHotelName;
    CardView manageNotifs;
    Button menuClick;
    Button bookCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            overridePendingTransition(0, 0);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(0, 0);
        } else {

            manageRooms = findViewById(R.id.manageRooms);
            manageNotifs = findViewById(R.id.manageNotifsCw);
            bookCardView=findViewById(R.id.valetRequestBtn);
            menuClick=findViewById(R.id.menuClick);

            menuClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(MainScreen.this, HotelMenu.class);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);


                }
            });

            bookCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(MainScreen.this, bookings.class);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);


                }
            });


            manageRooms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainScreen.this, hotelRoom.class);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);


                }
            });
            manageNotifs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainScreen.this, ServiceRequests.class);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
            });

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotels");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String AuthId = snapshot.child("authID").getValue().toString();
                            if (AuthId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                mainHotelName = snapshot.getKey();
                                Log.i("main name", mainHotelName);
                            }
                            //Log.i("data changed",snapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

}
