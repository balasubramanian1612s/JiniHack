package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodDelivery extends AppCompatActivity {

    ArrayList<FoodsGetter> foods=new ArrayList<>();
    ArrayList<FoodsGetter> special=new ArrayList<>();
    public static ArrayList<String> orders=new ArrayList<>();
    ListView foodsListView;
    ListView specialListView;
    FoodListAdapter foodListAdapter,foodListAdapter1;
    Button order;
    Button removeOrder;
    String dayNow;
    int currentHourIn24Format;
    String meal;
    String roomNo;
    LinearLayout rel1;
    LinearLayout rel2;
    LinearLayout lin;
    TextView specialsTw;
    TextView menuTw;


    String hotelName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_delivery);

        Intent intent=getIntent();
        hotelName=intent.getStringExtra("HotelName");

        foodsListView=findViewById(R.id.dailyMenuListView);
        foodListAdapter=new FoodListAdapter(this,R.layout.foods_custom_list,foods);
        foodsListView.setAdapter(foodListAdapter);
        order=findViewById(R.id.orderBtn);
        removeOrder=findViewById(R.id.removeOrderBTN);
        specialsTw=findViewById(R.id.specialstw);
        menuTw=findViewById(R.id.menutw);

        specialListView=findViewById(R.id.specialsListView);
        foodListAdapter1=new FoodListAdapter(this,R.layout.foods_custom_list,special);
        specialListView.setAdapter(foodListAdapter1);


        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                dayNow="Sunday";
                break;
            case Calendar.MONDAY:
                dayNow="Monday";
                break;
            case Calendar.TUESDAY:
                dayNow="Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayNow="Wednesday";
                break;
            case Calendar.THURSDAY:
                dayNow="Thursday";
                break;
            case Calendar.FRIDAY:
                dayNow="Friday";
                break;
            case Calendar.SATURDAY:
                dayNow="Saturday";
                break;
        }
        Log.i("dayToday",dayNow);

        getSpecialMenu();
        getMenu();

        /*Calendar rightNow = Calendar.getInstance();
        currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);

        if(currentHourIn24Format>=1&&currentHourIn24Format<=12){
            meal="Breakfast";
        }else if(currentHourIn24Format>=12&&currentHourIn24Format<=18){
            meal="Lunch";
        }else if(currentHourIn24Format>=18&&currentHourIn24Format<=24){
            meal="Dinner";
        }
        Log.i("currentHour",currentHourIn24Format+"");*/
        //Log.i("currentHour",currentHourIn24Format+"");
        //Log.i("meal",meal);

        final RelativeLayout layer=findViewById(R.id.layer);

        DatabaseReference dataRef=FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("foodDelivery").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Response");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    if(dataSnapshot.getValue().toString().equals("Accepted")){
                        layer.setVisibility(View.INVISIBLE);
                        specialListView.setVisibility(View.INVISIBLE);
                        foodsListView.setVisibility(View.INVISIBLE);
                        menuTw.setVisibility(View.INVISIBLE);
                        specialsTw.setText("You've a Pending order !");


                    }}

                else {
                    Toast.makeText(FoodDelivery.this, "NO REQUESTS!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("My Bookings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String hotel=snapshot.child("Hotel").getValue().toString();
                    if(hotel.equals(hotelName)){
                        roomNo=snapshot.child("RoomNo").getValue().toString();
                    }
                }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("foodDelivery").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference refo=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications").child("Services").child("foodDelivery").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(orders.size()>0) {

                    reference1.child("roomNo").setValue(roomNo);
                    reference1.child("Response").setValue("Not Accepted");
                    //reference1.child("roomNo").setValue()
                    for (String s : orders) {
                        reference1.child("Order").child(s).setValue("Ordered");
                    }
                    refo.child("roomNo").setValue(roomNo);
                    refo.child("Response").setValue("Not Accepted");
                    //reference1.child("roomNo").setValue()
                    for (String s : orders) {
                        refo.child("Order").child(s).setValue("Ordered");
                    }
                    orders.clear();
                }
            }
        });


        removeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1.setValue(null);
                refo.setValue(null);
            }
        });


    }

    private void getMenu() {



        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Food Menu").child("Daily").child(dayNow);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("Data","Changed");
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String name=snapshot.child("Name").getValue().toString();
                    String desc=snapshot.child("Desc").getValue().toString();
                    String price=snapshot.child("Price").getValue().toString();

                    foods.add(new FoodsGetter(name,price,desc));
                    foodListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void getSpecialMenu() {



        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Food Menu").child("Specials").child(dayNow);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("Data","Changed");
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String name=snapshot.child("Name").getValue().toString();
                    String desc=snapshot.child("Desc").getValue().toString();
                    String price=snapshot.child("Price").getValue().toString();

                    special.add(new FoodsGetter(name,price,desc));
                    foodListAdapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}