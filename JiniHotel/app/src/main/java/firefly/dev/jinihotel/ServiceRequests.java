package firefly.dev.jinihotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServiceRequests extends AppCompatActivity {
    Button laundry;
    Button food;
    Button valet;
    Button roomService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_requests);

        laundry=findViewById(R.id.laundryRequestBtn);
        food=findViewById(R.id.foodDeliveryRequestBtn);
        roomService=findViewById(R.id.roomServiceRequestBtn);
        valet=findViewById(R.id.valetRequestBtn);

        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LaundryRequests.class));
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FoodDeliveryRequests.class));
            }
        });
        roomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RoomServiceRequests.class));
            }
        });
        valet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ValetRequests.class));
            }
        });
    }
}
