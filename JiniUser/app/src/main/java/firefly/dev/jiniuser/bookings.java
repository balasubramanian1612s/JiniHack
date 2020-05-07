package firefly.dev.jiniuser;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class bookings extends AppCompatActivity {

    public static Context topBarContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);



        topBarContext=bookings.this;
        Log.i("I am", "Here");


        TabLayout tabLayout=findViewById(R.id.topTab);

        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.tabFrameContainer,new bookedFragment());
        transaction.commit();


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.orderMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:
                        startActivity(new Intent(getApplicationContext(),all_hotels.class));
                        overridePendingTransition(0,0);


                    case R.id.orderMenu:
                        startActivity(new Intent(getApplicationContext(),bookings.class));
                        overridePendingTransition(0,0);



                }


                return false;
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().equals("Booked")){

                    FragmentManager manager=getFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.tabFrameContainer,new bookedFragment());
                    transaction.commit();

                }
                if (tab.getText().equals("Upcoming")){

                    FragmentManager manager=getFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.tabFrameContainer,new upcomingFragment());
                    transaction.commit();

                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });







    }
}
