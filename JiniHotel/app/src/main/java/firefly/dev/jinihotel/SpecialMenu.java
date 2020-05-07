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

public class SpecialMenu extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ListView specialsListView;
    ArrayList<String> foodsSpecial=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_menu);
        floatingActionButton=findViewById(R.id.floatingActionButton);

        specialsListView=findViewById(R.id.dailysListView);
        final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,foodsSpecial);
        specialsListView.setAdapter(arrayAdapter);

        //final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child("Farfield").child("Food Menu").child("Specials").child(HotelMenu.day).child(HotelMenu.food);
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(mainHotelName).child("Food Menu").child("Specials").child(HotelMenu.day);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodsSpecial.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    String foods=dataSnapshot1.getKey();
                    foodsSpecial.add(foods);
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

                AlertDialog.Builder alert = new AlertDialog.Builder(SpecialMenu.this);
                alert.setTitle("Add Food Item");
                // this is set the view from XML inside AlertDialog
                alert.setView(view);
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
                            foodsSpecial.add(sName);
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

       specialsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               Log.i("Long clicked",position+"");

               AlertDialog.Builder alert = new AlertDialog.Builder(SpecialMenu.this);

               alert.setTitle("Are you sure?");
               // this is set the view from XML inside AlertDialog
               // disallow cancel of AlertDialog on click of back button and outside touch
               alert.setCancelable(false);
               alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       if(!foodsSpecial.isEmpty()) {
                           reference.child(foodsSpecial.get(position)).setValue(null);
                           foodsSpecial.remove(position);
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
