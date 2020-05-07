package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class roomViewPage extends AppCompatActivity {

    TextView roomtarrif,roomBedType,roomComplementry,roomLaundry,roomWifi,roomPool,roomSize;
    Button bookRoom;
    FirebaseDatabase database;
    DatabaseReference reference;
    ViewPager roomViewPager;

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url=new URL(urls[0]);

                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                InputStream in=httpURLConnection.getInputStream();

                Bitmap myBitmap= BitmapFactory.decodeStream(in);
                return myBitmap;


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_view_page);

        final Intent intent=getIntent();
        final String roomName=intent.getStringExtra("RoomName");
        final String hotelName=intent.getStringExtra("HotelName");
        //final String hotelName="Farfield";
        //final String roomName="Penthouse";

        roomtarrif=findViewById(R.id.roomtarrif);
        roomBedType=findViewById(R.id.roomBedType);
        roomComplementry=findViewById(R.id.roomComplementry);
        roomLaundry=findViewById(R.id.roomLaundry);
        roomWifi=findViewById(R.id.roomWifi);
        roomPool=findViewById(R.id.roomPool);
        roomSize=findViewById(R.id.roomSize);
        bookRoom=findViewById(R.id.bookRoom);
        roomViewPager=findViewById(R.id.roomViewPager);


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


        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(hotelName).child("Rooms").child(roomName);

        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(roomViewPage.this,bookingInfo.class);
                intent1.putExtra("RoomName",roomName);
                intent1.putExtra("HotelName",hotelName);
                startActivity(intent1);

            }
        });

        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomtarrif.setText("INR: "+ dataSnapshot.child("Tarrif").getValue().toString());
                AmenitiesGetter getter=dataSnapshot.child("Amenities").getValue(AmenitiesGetter.class);
                roomBedType.setText(getter.getBedType());
                roomComplementry.setText(getter.getComplementry());
                roomLaundry.setText(getter.getFreeLaundry());
                roomPool.setText(getter.getPool());
                roomWifi.setText(getter.getFreeWifi());
                roomSize.setText(getter.getRoomSize());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(eventListener);

        DatabaseReference ref=database.getReference().child("Hotels").child(hotelName).child("Rooms").child(roomName).child("picLinks");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    try {
                        ArrayList<Bitmap> images=new ArrayList<>();

                        String urls = dataSnapshot.getValue().toString();
                        Log.i("URL",urls);
                        String url[] = urls.split(",");
                        for (String string : url) {
                            ImageDownloader imageDownloader = new ImageDownloader();
                            Log.i("Link", string);
                            Bitmap bitmap = imageDownloader.execute(string).get();
                            Log.i("Dwlnd","Success");
                            images.add(bitmap);
                        }
                        UserCustomPagerAdapter userCustomPagerAdapter=new UserCustomPagerAdapter(images,getApplicationContext());
                        roomViewPager.setAdapter(userCustomPagerAdapter);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
