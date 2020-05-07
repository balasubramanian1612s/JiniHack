package firefly.dev.jiniuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomService extends AppCompatActivity {

    Button callRoomServiceBtn;
    TextView statusTw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_service);

        Intent intent=getIntent();
        String hotelName=intent.getStringExtra("HotelName");

        callRoomServiceBtn=findViewById(R.id.callRoomServiceBtn);
        statusTw=findViewById(R.id.statusTw);

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelName).child("Notifications").child("Services").child("roomService").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications").child("Services").child("roomService").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getValue().toString().equals("Accepted")) {
                            statusTw.setText("You've already notified the reception !");
                            callRoomServiceBtn.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        callRoomServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("Response").setValue("Not Accepted");
                ref.child("roomNo").setValue("123");

                userRef.child("Response").setValue("Not Accepted");
                userRef.child("roomNo").setValue("123");

                statusTw.setText("Room Service has been notified !");
                callRoomServiceBtn.setEnabled(false);

            }
        });
    }
}
