package firefly.dev.jiniuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class qrScan extends AppCompatActivity {

    Button openRoomWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        openRoomWv=findViewById(R.id.openRoomWv);
        openRoomWv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(qrScan.this,wbRoomUnlock.class);
                startActivity(intent);


            }
        });




    }
}
