package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import firefly.dev.jinihotel.adapters.FoodDeliveryAdapter;
import firefly.dev.jinihotel.getters.FoodDeliveryGetter;
import firefly.dev.jinihotel.getters.RoomServiceGetter;

public class FoodDeliveryRequests extends AppCompatActivity {
    ArrayList<FoodDeliveryGetter> foodDelivery=new ArrayList<>();
    public static ArrayList<String> order=new ArrayList<>();
    public static  String hotelName;
    public static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_delivery_requests);

        hotelName=MainScreen.mainHotelName;
        ListView foodDeliveryList=findViewById(R.id.foodDeliveryListView);
        final ListView orderContents=findViewById(R.id.ordercontentListView);

        final FoodDeliveryAdapter foodDeliveryAdapter=new FoodDeliveryAdapter(this,R.layout.food_delivery_custom_list,foodDelivery);
        foodDeliveryList.setAdapter(foodDeliveryAdapter);

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,order);
        orderContents.setAdapter(arrayAdapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("foodDelivery");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodDelivery.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String roomNo = snapshot.child("roomNo").getValue().toString();

                        foodDelivery.add(new FoodDeliveryGetter(roomNo, snapshot.getKey()));
                        foodDeliveryAdapter.notifyDataSetChanged();
                    }



                }else{
                    Toast.makeText(FoodDeliveryRequests.this, "NO REQUESTS !", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
