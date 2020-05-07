package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static firefly.dev.jinihotel.MainScreen.mainHotelName;

public class DailyMenu extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ListView dailysListView;
    ArrayList<String> foodsDaily=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_menu);
        floatingActionButton=findViewById(R.id.floatingActionButton);

        dailysListView=findViewById(R.id.dailysListView);
        final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,foodsDaily);
        dailysListView.setAdapter(arrayAdapter);

        //final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child("Farfield").child("Food Menu").child("Daily").child(HotelMenu.day).child(HotelMenu.food);
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(mainHotelName).child("Food Menu").child("Daily").child(HotelMenu.day);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodsDaily.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String foods=dataSnapshot1.getKey();
                    foodsDaily.add(foods);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View view=layoutInflater.inflate(R.layout.custom_alert_dialog,null);
                final EditText name=view.findViewById(R.id.specialName);
                final EditText desc=view.findViewById(R.id.specialDescription);
                final EditText price=view.findViewById(R.id.specialPrice);

                AlertDialog.Builder alert = new AlertDialog.Builder(DailyMenu.this);
                alert.setTitle("Add Food Item");
                // this is set the view from XML inside AlertDialog
                alert.setView(view);
                alert.setMessage("Add a few details about the delicacy!");
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sName=name.getText().toString();
                        String sDesc=desc.getText().toString();
                        String sPrice=price.getText().toString();

                        if(sName!=null&&sDesc!=null&&sPrice!=null) {
                            foodsDaily.add(sName);
                            reference.child(sName).child("Name").setValue(sName);
                            reference.child(sName).child("Desc").setValue(sDesc);
                            reference.child(sName).child("Price").setValue(sPrice);
                            arrayAdapter.notifyDataSetChanged();

                            Log.i("Added", sName + sDesc + sPrice);
                        }
                    }
                });
                alert.create().show();


            }
        });

        dailysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("Long clicked",position+"");

                AlertDialog.Builder alert = new AlertDialog.Builder(DailyMenu.this);

                alert.setTitle("Are you sure?");
                // this is set the view from XML inside AlertDialog
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!foodsDaily.isEmpty()) {
                            reference.child(foodsDaily.get(position)).setValue(null);
                            foodsDaily.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();

            }
        });
    }
}



