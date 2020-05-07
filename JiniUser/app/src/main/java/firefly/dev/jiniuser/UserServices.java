package firefly.dev.jiniuser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class UserServices extends AppCompatActivity {

    Button foodDeliveryBtn2,roomServiceBtn,valetBtn2,laundryBtn,botBtn,unlock_qrBtn;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_services);
        foodDeliveryBtn2=findViewById(R.id.foodDeliveryBtn2);
        roomServiceBtn=findViewById(R.id.roomServiceBtn);
        valetBtn2=findViewById(R.id.valetBtn2);
        laundryBtn=findViewById(R.id.laundryBtn);
        botBtn=findViewById(R.id.botBtn);
        unlock_qrBtn=findViewById(R.id.unlock_qrBtn);



        final Intent intent=getIntent();
        String hotelName=intent.getStringExtra("HotelName");


        if (hotelName.equals(null)){

            hotelName=preferences.getString("HotelName",hotelName);


        }else {

            preferences=getSharedPreferences("HotelName", MODE_PRIVATE);
            editor=preferences.edit();
            editor.putString("HotelName",hotelName).commit();

        }


        unlock_qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2=new Intent(UserServices.this,qrScan.class);
                startActivity(intent2);

            }
        });

        botBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(UserServices.this,chatBotFirefly.class);
                startActivity(intent1);

            }
        });


        final String finalHotelName = hotelName;
        foodDeliveryBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UserServices.this,FoodDelivery.class);
                overridePendingTransition(0,0);
                intent.putExtra("HotelName", finalHotelName);
                startActivity(intent);

                overridePendingTransition(0,0);

            }
        });

        final String finalHotelName1 = hotelName;
        roomServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UserServices.this,RoomService.class);
                overridePendingTransition(0,0);
                intent.putExtra("HotelName", finalHotelName1);
                startActivity(intent);
                overridePendingTransition(0,0);

            }
        });

        valetBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UserServices.this,ValetService.class);
                overridePendingTransition(0,0);
                intent.putExtra("HotelName",finalHotelName);
                startActivity(intent);
                overridePendingTransition(0,0);


            }
        });

        laundryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(UserServices.this,LaundryService.class);
                overridePendingTransition(0,0);
                intent.putExtra("HotelName",finalHotelName);
                startActivity(intent);
                overridePendingTransition(0,0);


            }
        });



    }

  
}
