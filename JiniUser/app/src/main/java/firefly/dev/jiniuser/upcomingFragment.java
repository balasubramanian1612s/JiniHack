package firefly.dev.jiniuser;

import android.app.Fragment;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import firefly.dev.jiniuser.bookingsAdapter;
import firefly.dev.jiniuser.bookedRoomsGetter;


/**
 * A simple {@link Fragment} subclass.
 */
public class upcomingFragment extends Fragment {


    ListView upcomingList;
    ArrayList<bookedRoomsGetter> getters=new ArrayList<>();
    bookingsAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    SimpleDateFormat sdf = null;
    Date date = null;
    Date date1 = null;
    String today;
    String currentDateandTime;
    ArrayList<String> hotelNames=new ArrayList<>();




    public upcomingFragment() {
        // Required empty public constructor

        Log.i("I am", "Here");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingList=view.findViewById(R.id.upcomingList);

        Log.i("I am", "Here");

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("My Bookings");
        getData();

        adapter=new bookingsAdapter(getActivity(),R.layout.booked_items_row,getters,hotelNames);
        upcomingList.setAdapter(adapter);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("dd-MM-yyyy");
            currentDateandTime = sdf.format(new Date());
            try {
                date=sdf.parse(currentDateandTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return view;



    }

    private void getData(){


        ChildEventListener listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                bookedRoomsGetter newGetter=dataSnapshot.getValue(bookedRoomsGetter.class);
                String bookDay=newGetter.getBookDate();
                Log.i("jsnkdd",bookDay);
                Log.i("jsnkdd",currentDateandTime);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    try {
                        date1 = sdf.parse(bookDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (date1.after(date)){


                    getters.add(newGetter);
                    hotelNames.add(dataSnapshot.child("Hotel").getValue().toString());
                    Log.i("HotelName",newGetter.getHotel());
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(listener);


    }
}
