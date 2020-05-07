package firefly.dev.jinihotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Hotel_Info extends AppCompatActivity {

    FirebaseDatabase database;
    EditText hotelName;
    EditText hotelAddress;
    RadioGroup checkoutRadioGrp;
    RadioGroup parkingRadioGrp;
    RadioGroup poolRadioGrp;
    Button nextButton;
    RatingBar ratingBar;
    public static  String nameHotel;
    EditText emailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_info);

        database=FirebaseDatabase.getInstance();

        hotelName=findViewById(R.id.hotelNameEt);
        hotelAddress=findViewById(R.id.hotelAddressEt);
        checkoutRadioGrp=findViewById(R.id.checkoutRadioGrp);
        parkingRadioGrp=findViewById(R.id.parkingRadioGrp);
        poolRadioGrp=findViewById(R.id.poolRadioGrp);
        nextButton=findViewById(R.id.nxtBtn);
        ratingBar=findViewById(R.id.ratingBar);
        emailID=findViewById(R.id.emailIDet);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id1 = checkoutRadioGrp.getCheckedRadioButtonId();
                RadioButton rb1 = (RadioButton) findViewById(id1);
                int id2 = poolRadioGrp.getCheckedRadioButtonId();
                RadioButton rb2  = (RadioButton) findViewById(id2);
                int id3 = parkingRadioGrp.getCheckedRadioButtonId();
                RadioButton rb3 = (RadioButton) findViewById(id3);

                String checkOut=rb1.getText().toString();
                String parking=rb3.getText().toString();;
                String pool=rb2.getText().toString();;
                String name=hotelName.getText().toString();
                nameHotel=name;
                String address=hotelAddress.getText().toString();
                String rating=ratingBar.getRating()+"";
                String email=emailID.getText().toString();

                database.getReference().child("Hotels").child(name).child("Info").child("hotelName").setValue(name);
                database.getReference().child("Hotels").child(name).child("Info").child("hotelAddress").setValue(address);
                database.getReference().child("Hotels").child(name).child("Info").child("checkOut").setValue(checkOut);
                database.getReference().child("Hotels").child(name).child("Info").child("pool").setValue(pool);
                database.getReference().child("Hotels").child(name).child("Info").child("rating").setValue(rating);
                database.getReference().child("Hotels").child(name).child("Info").child("parking").setValue(parking);
                database.getReference().child("Hotels").child(name).child("Info").child("emailId").setValue(email);
                database.getReference().child("Hotels").child(name).child("authID").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                startActivity(new Intent(getApplicationContext(),HotelLocation.class));
            }
        });



    }
}
