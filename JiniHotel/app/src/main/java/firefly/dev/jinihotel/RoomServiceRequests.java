package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import firefly.dev.jinihotel.adapters.RoomServiceListAdapter;
import firefly.dev.jinihotel.getters.RoomServiceGetter;

public class RoomServiceRequests extends AppCompatActivity {
    ArrayList<RoomServiceGetter> roomService=new ArrayList<>();
    public static  String hotelName;
    ListView roomServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_service_requests);

        roomServiceList=findViewById(R.id.roomServiceListView);
        hotelName=MainScreen.mainHotelName;

        final RoomServiceListAdapter roomServiceListAdapter=new RoomServiceListAdapter(this,R.layout.room_service_custom_list,roomService);
        roomServiceList.setAdapter(roomServiceListAdapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("roomService");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomService.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String roomNo = snapshot.child("roomNo").getValue().toString();

                        roomService.add(new RoomServiceGetter(roomNo, snapshot.getKey()));
                        roomServiceListAdapter.notifyDataSetChanged();
                    }


                }else{
                    Toast.makeText(RoomServiceRequests.this, "NO REQUESTS !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
