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

import firefly.dev.jinihotel.adapters.ValetListAdapter;
import firefly.dev.jinihotel.getters.LaundryGetter;
import firefly.dev.jinihotel.getters.ValetGetter;

public class ValetRequests extends AppCompatActivity {
    public  static  String hotelName;
    ListView valetList;
    ArrayList<ValetGetter> valet=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valet_requests);

        hotelName=MainScreen.mainHotelName;//need to be made dynamic
        valetList=findViewById(R.id.valetList);

        final ValetListAdapter valetListAdapter=new ValetListAdapter(this,R.layout.valet_custom_list,valet);
        valetList.setAdapter(valetListAdapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("valetServices");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                valet.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String username = snapshot.child("userName").getValue().toString();
                        String time = snapshot.child("Time").getValue().toString();
                        String regNo = snapshot.child("regNo").getValue().toString();
                        String tokenNo = snapshot.child("tokenNo").getValue().toString();

                        valet.add(new ValetGetter(username, regNo, tokenNo, time, snapshot.getKey()));
                        valetListAdapter.notifyDataSetChanged();

                    }

                }else{
                    Toast.makeText(ValetRequests.this, "NO REQUESTS !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
