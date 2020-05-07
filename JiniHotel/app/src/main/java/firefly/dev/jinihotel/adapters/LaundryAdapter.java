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

import firefly.dev.jinihotel.LaundryRequests;
import firefly.dev.jinihotel.R;
import firefly.dev.jinihotel.getters.LaundryGetter;

public class LaundryAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<LaundryGetter> laundry;

    public LaundryAdapter(@NonNull Context context, int resource, ArrayList<LaundryGetter> laundry) {
        super(context, resource, laundry);
        this.context = context;
        this.resource = resource;
        this.laundry = laundry;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);
        TextView roomNo=view.findViewById(R.id.roomNoTw);
        TextView time=view.findViewById(R.id.timeTw);
        final Button acceptBtn=view.findViewById(R.id.acceptBtn);
        roomNo.setText("Room No. :"+laundry.get(position).getRoomNo());
        time.setText(laundry.get(position).getTime());

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Hotels").child(LaundryRequests.hotelname).child("Notifications").child("Services").child("laundryServices").child(laundry.get(position).getAuthID()).child("Response");
        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("User").child(laundry.get(position).getAuthID()).child("Notifications").child("Services").child("laundryServices").child(laundry.get(position).getAuthID()).child("Response");
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
