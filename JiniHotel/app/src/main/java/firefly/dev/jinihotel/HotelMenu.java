package firefly.dev.jinihotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HotelMenu extends AppCompatActivity {

    Button editMenuBtn;
    Button todayMenuBtn;
    Button specialMenuBtn;
    public static String day;
    public static String food;
    ArrayList<String> days=new ArrayList<>();
    Spinner spinner;
    FloatingActionButton floatingActionButton;
    RadioGroup foodRadioGrp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_menu);

        editMenuBtn=findViewById(R.id.editMenuButn);
        todayMenuBtn=findViewById(R.id.dailyMenuBtn);
        specialMenuBtn=findViewById(R.id.specialsMenuBtn);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        spinner=findViewById(R.id.spinner);
        days.add("Choose a day");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, days);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        spinner.setAdapter(adp);

        editMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayMenuBtn.setVisibility(View.VISIBLE);
                specialMenuBtn.setVisibility(View.VISIBLE);


                if(!spinner.getSelectedItem().toString().equals("Choose a day")){
                    day=spinner.getSelectedItem().toString();
                }


            }
        });
        todayMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DailyMenu.class));
            }
        });

        specialMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SpecialMenu.class));
            }
        });


    }
}
