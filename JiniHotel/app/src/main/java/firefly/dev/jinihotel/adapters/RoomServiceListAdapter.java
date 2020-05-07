package firefly.dev.jinihotel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import firefly.dev.jinihotel.R;
import firefly.dev.jinihotel.RoomServiceRequests;
import firefly.dev.jinihotel.ValetRequests;
import firefly.dev.jinihotel.getters.RoomServiceGetter;
import firefly.dev.jinihotel.getters.ValetGetter;

public class RoomServiceListAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<RoomServiceGetter> roomService;

    public RoomServiceListAdapter(@NonNull Context context, int resource, ArrayList<RoomServiceGetter> roomService) {
        super(context, resource, roomService);
        this.context = context;
        this.resource = resource;
        this.roomService = roomService;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);
        final Button acceptBtn=view.findViewById(R.id.acceptServiceBtn);
        TextView roomNo=view.findViewById(R.id.roomNoServTw);
        roomNo.setText("Room No: "+roomService.get(position).getRoomNo());
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(RoomServiceRequests.hotelName).child("Notifications").child("Services").child("roomService").child(roomService.get(position).getAuthId()).child("Response");
        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("User").child(roomService.get(position).getAuthId()).child("Notifications").child("Services").child("roomService").child(roomService.get(position).getAuthId()).child("Response");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue().toString().equals("Accepted")){
                    acceptBtn.setText("Accepted");
                    acceptBtn.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue("Accepted");
                ref.setValue("Accepted");
                acceptBtn.setText("Accepted");
                acceptBtn.setEnabled(false);
                //Log.i("HotelName",LaundryRequests.hotelname);
                //Log.i("AuthId",laundry.get(position).getAuthID());
            }
        });
        return  view;
    }
}
