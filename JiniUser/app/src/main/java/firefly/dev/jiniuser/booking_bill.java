package firefly.dev.jiniuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class booking_bill extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_bill);






    };


    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,bookings.class);
        startActivity(intent);

    }
}
