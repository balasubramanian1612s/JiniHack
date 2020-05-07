package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import firefly.dev.jinihotel.adapters.LaundryAdapter;
import firefly.dev.jinihotel.getters.LaundryGetter;

public class LaundryRequests extends AppCompatActivity {

    ArrayList<LaundryGetter> laundry=new ArrayList<>();
    public static String hotelname;
    ListView launList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laundry_requests);

        hotelname=MainScreen.mainHotelName;//static ,should be changed to dynamic later
        launList=findViewById(R.id.laundryListView);

        final LaundryAdapter laundryAdapter=new LaundryAdapter(this,R.layout.request_custom_list,laundry);
        launList.setAdapter(laundryAdapter);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelname).child("Notifications").child("Services").child("laundryServices");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                laundry.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String roomNo = snapshot.child("RoomNo").getValue().toString();
                        String time = snapshot.child("Time").getValue().toString();

                        laundry.add(new LaundryGetter(roomNo, time, snapshot.getKey()));
                        laundryAdapter.notifyDataSetChanged();

                    }
                }else{
                    Toast.makeText(LaundryRequests.this, "NO REQUESTS !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
