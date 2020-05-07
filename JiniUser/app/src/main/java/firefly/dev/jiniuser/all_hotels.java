package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class all_hotels extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    hotelListAdapter adapter;
    ListView allHotelsList;
    GifImageView gifImageView;
    ArrayList<hotelInfoGetter> getters=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotels);

        gifImageView=findViewById(R.id.gifImageView);

        allHotelsList=findViewById(R.id.allHotelsList);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels");

        adapter=new hotelListAdapter(this,R.layout.all_hotels_row,getters);
        getters.clear();
        allHotelsList.setAdapter(adapter);

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



        allHotelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent=new Intent(all_hotels.this,hotelViewPage.class);
                intent.putExtra("HotelName",getters.get(i).getHotelName());
                startActivity(intent);


            }
        });



        ChildEventListener eventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                hotelInfoGetter getter=dataSnapshot.child("Info").getValue(hotelInfoGetter.class);
                getters.add(getter);
                adapter.notifyDataSetChanged();
                gifImageView.setVisibility(View.INVISIBLE);
                Log.i("hsjdnks",getter.getHotelName());


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
        reference.addChildEventListener(eventListener);






    }
}
