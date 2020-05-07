package firefly.dev.jinihotel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class bookings extends AppCompatActivity {

    public static Context topBarContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);



        topBarContext=bookings.this;


        TabLayout tabLayout=findViewById(R.id.topTab);

        android.app.FragmentManager manager=getFragmentManager();
        android.app.FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.tabFrameContainer,new bookedFragment());
        transaction.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().equals("Booked")){

                    android.app.FragmentManager manager=getFragmentManager();
                    android.app.FragmentTransaction transaction=manager.beginTransaction();
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
