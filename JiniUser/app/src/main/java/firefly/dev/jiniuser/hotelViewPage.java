package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class hotelViewPage extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference, roomReff;
    TextView hotelNameTv1,hotelAddressTv1,ParkingTv,swimmingPoolTv,starCategory;
    ListView allRoomsLV;
    ArrayList<String> tarrifsArray=new ArrayList<>();
    ArrayList<String> roomTypeArray=new ArrayList<>();
    hotelRoomAdapter adapter;
    ViewPager hotelImagePager;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_view_page);
        hotelNameTv1=findViewById(R.id.hotelNameTv1);
        hotelAddressTv1=findViewById(R.id.hotelAddressTv1);
        ParkingTv=findViewById(R.id.swimmingPoolTv);
        swimmingPoolTv=findViewById(R.id.swimmingPoolTv);
        starCategory=findViewById(R.id.starCategory);
        allRoomsLV=findViewById(R.id.allRoomsLV);
        hotelImagePager=findViewById(R.id.hotelViewPager);


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



        Intent intent=getIntent();
        final String hotelName=intent.getStringExtra("HotelName");
        //final String hotelName="Farfield";

        allRoomsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(hotelViewPage.this,roomViewPage.class);
                intent.putExtra("RoomName",roomTypeArray.get(i));
                intent.putExtra("HotelName",hotelName);
                startActivity(intent);


            }
        });



        adapter=new hotelRoomAdapter(this,R.layout.room_row,tarrifsArray,roomTypeArray);
        allRoomsLV.setAdapter(adapter);


        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(hotelName);
        roomReff=database.getReference().child("Hotels").child(hotelName);



        /*ChildEventListener eventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSnapshot=dataSnapshot.child(hotelName);
                Log.i("sdfsdmf",dataSnapshot.getKey());
               try {
                   hotelInfoGetter getter=dataSnapshot.child("Info").getValue(hotelInfoGetter.class);
                   hotelNameTv1.setText(getter.getHotelName());
                   hotelAddressTv1.setText(getter.getHotelAddress());
                   ParkingTv.setText(getter.getParking());
                   starCategory.setText(getter.getRating());
                   swimmingPoolTv.setText(getter.getPool());

                   dataSnapshot=dataSnapshot.child("Rooms");
                   roomTypeArray.add(dataSnapshot.getKey());
                   tarrifsArray.add("INR: "+dataSnapshot.child("Tarrif").getValue().toString());
                   adapter.notifyDataSetChanged();
               }catch (Exception e){
                   e.printStackTrace();

               }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(eventListener);*/



        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    hotelInfoGetter getter=dataSnapshot.child("Info").getValue(hotelInfoGetter.class);
                    hotelNameTv1.setText(getter.getHotelName());
                    hotelAddressTv1.setText(getter.getHotelAddress());
                    ParkingTv.setText(getter.getParking());
                    starCategory.setText(getter.getRating());
                    swimmingPoolTv.setText(getter.getPool());
                roomReff=roomReff.child("Rooms");
                getRoomData();


            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);

        DatabaseReference ref=database.getReference().child("Hotels").child(hotelName).child("Info").child("picLinks");
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
                            hotelViewPage.ImageDownloader imageDownloader = new ImageDownloader();
                            Log.i("Link", string);
                            Bitmap bitmap = imageDownloader.execute(string).get();
                            Log.i("Dwlnd","Success");
                            images.add(bitmap);
                        }
                        UserCustomPagerAdapter userCustomPagerAdapter=new UserCustomPagerAdapter(images,getApplicationContext());
                        hotelImagePager.setAdapter(userCustomPagerAdapter);

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

    private void getRoomData(){

        ChildEventListener listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {





                try {
                    roomTypeArray.add(dataSnapshot.getKey());
                    tarrifsArray.add("INR: "+dataSnapshot.child("Tarrif").getValue().toString());
                    adapter.notifyDataSetChanged();

                    AmenitiesGetter getter=dataSnapshot.child("Amenities").getValue(AmenitiesGetter.class); }catch (Exception e){

                    startActivity(getIntent());

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
        roomReff.addChildEventListener(listener);




    }


}
