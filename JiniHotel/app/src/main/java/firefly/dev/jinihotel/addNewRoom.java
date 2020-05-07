package firefly.dev.jinihotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firefly.dev.jinihotel.getters.AmenitiesGetter;

public class addNewRoom extends AppCompatActivity {

    EditText roomName,tarrif,bedType,roomSize;
    Spinner SpinWifi,SpinLaundry,SpinPool;
    Button addRoomdata;
    CheckBox dinnerCB,breakfastCB,lunchCB,brunchCB,eveningSnackCB;
    String availability[]={"Yes","No"};
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_room);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child("HotelId").child("Rooms");


        roomName=findViewById(R.id.roomName);
        roomSize=findViewById(R.id.roomSize);
        tarrif=findViewById(R.id.tarrif);
        bedType=findViewById(R.id.bedType);
        SpinWifi=findViewById(R.id.SpinWifi);
        SpinLaundry=findViewById(R.id.SpinLaundry);
        SpinPool=findViewById(R.id.SpinPool);
        dinnerCB=findViewById(R.id.dinnerCB);
        breakfastCB=findViewById(R.id.breakfastCB);
        lunchCB=findViewById(R.id.lunchCB);
        brunchCB=findViewById(R.id.brunchCB);
        eveningSnackCB=findViewById(R.id.eveningSnackCB);
        addRoomdata=findViewById(R.id.addRoomdata);

        ArrayAdapter spinAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,availability);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinPool.setAdapter(spinAdapter);
        SpinLaundry.setAdapter(spinAdapter);
        SpinWifi.setAdapter(spinAdapter);

        addRoomdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference=reference.child(roomName.getText().toString());
                reference.child("Tarrif").setValue(tarrif.getText().toString());
                reference=reference.child("Amenities");

                String com="";
                if (dinnerCB.isChecked()){

                    com=com+", DCB";

                }
                if (breakfastCB.isChecked()){

                    com=com+", BCB";

                }
                if (brunchCB.isChecked()){

                    com=com+", BRCB";

                }
                if (lunchCB.isChecked()){

                    com=com+", LCB";

                }
                if (eveningSnackCB.isChecked()){

                    com=com+", ESCB";

                }


                AmenitiesGetter getter=new AmenitiesGetter(com,SpinLaundry.getSelectedItem().toString(),SpinWifi.getSelectedItem().toString(),SpinPool.getSelectedItem().toString());
                reference.setValue(getter);

                Intent intent=new Intent(addNewRoom.this,hotelRoom.class);
                startActivity(intent);

            }
        });





    }
}
