package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import firefly.dev.jinihotel.adapters.hotelRoomAdapter;
import firefly.dev.jinihotel.getters.AmenitiesGetter;

import static firefly.dev.jinihotel.MainScreen.mainHotelName;

public class hotelRoom extends AppCompatActivity {

    FirebaseDatabase database;
    ArrayList<String> tarrifsArray=new ArrayList<>();
    ArrayList<String> roomTypeArray=new ArrayList<>();
    hotelRoomAdapter adapter;
    AlertDialog.Builder builder;
    DatabaseReference reference;
    FloatingActionButton floatingActionButton;
    ListView hotelRoomsList;
    ProgressDialog progressDialog;
    private static int i = 1;
    private static final String chan_ID = "1234";
    private static final String chan_name = "test";
    private static final String chan_desc = "descrbe";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_room);
        floatingActionButton=findViewById(R.id.addRoom);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(mainHotelName).child("Rooms");
        tarrifsArray.clear();
        roomTypeArray.clear();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(true); // disable dismiss by tapping outside of the dialog
        progressDialog.show();
// To dismiss the dialog





        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:


                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(),MainScreen.class));
                        overridePendingTransition(0,0);



                }


                return false;
            }
        });





        builder=new AlertDialog.Builder(this);
        adapter=new hotelRoomAdapter(this,R.layout.room_row,tarrifsArray,roomTypeArray);
        hotelRoomsList=findViewById(R.id.hotelRoomsList);
        hotelRoomsList.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(hotelRoom.this,addNewRoom.class);
                startActivity(intent);

            }
        });


        hotelRoomsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub

                builder.setMessage("Do You Want To delete This Room ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String delRoom =roomTypeArray.get(pos);
                                reference.child(delRoom).setValue(null);
                                Toast.makeText(getApplicationContext(),"Room Will be Deleted!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();


                return true;
            }
        });



        ChildEventListener listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                roomTypeArray.add(dataSnapshot.getKey());
                tarrifsArray.add("INR: "+dataSnapshot.child("Tarrif").getValue().toString());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

                AmenitiesGetter getter=dataSnapshot.child("Amenities").getValue(AmenitiesGetter.class);
                try {
                    Log.i("jnasas",getter.toString());}catch (Exception e){

                }




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                roomTypeArray.remove(dataSnapshot.getKey());
                tarrifsArray.remove("INR: "+dataSnapshot.child("Tarrif").getValue().toString());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(listener);


    }




}
