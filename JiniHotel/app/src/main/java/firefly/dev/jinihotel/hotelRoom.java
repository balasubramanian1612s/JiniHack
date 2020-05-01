package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firefly.dev.jinihotel.getters.AmenitiesGetter;

public class hotelRoom extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    FloatingActionButton floatingActionButton;



    private static final String chan_ID = "1234";
    private static final String chan_name = "test";
    private static final String chan_desc = "descrbe";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_room);
        floatingActionButton=findViewById(R.id.addRoom);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(hotelRoom.this,addNewRoom.class);
                startActivity(intent);

            }
        });



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel chan1 = new NotificationChannel(chan_ID,chan_name, NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setDescription( chan_desc );
            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel(chan1);
        }




        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child("HotelId").child("Rooms");

        getData();


    }

    public void getData(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    Log.i("StringsKey",ds.getKey());
                    display(ds.getKey(),"Hi");
                    AmenitiesGetter newData=ds.child("Amenities").getValue(AmenitiesGetter.class);
                    Log.i("StringsKey",newData.getComplementry());
                    Log.i("StringsLan",newData.getFreeLaundry());
                    Log.i("StringsWifi",newData.getFreeWifi());
                    Log.i("StringsPool",newData.getPool());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void display(String title,String text){

        NotificationCompat.Builder  bui2;
        bui2 = new NotificationCompat.Builder(this,chan_ID )
                .setContentTitle( title )
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1,bui2.build());
    }


}
