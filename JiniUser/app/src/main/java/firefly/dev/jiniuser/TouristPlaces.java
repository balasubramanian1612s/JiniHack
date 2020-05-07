package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TouristPlaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_tourist_places);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:


                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(),bookings.class));
                        overridePendingTransition(0,0);



                }


                return false;
            }
        });

        


    }
}
