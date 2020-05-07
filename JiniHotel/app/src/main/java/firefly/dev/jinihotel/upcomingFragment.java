package firefly.dev.jinihotel;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import firefly.dev.jinihotel.adapters.bookingsAdapter;
import firefly.dev.jinihotel.getters.bookedRoomsGetter;

import static firefly.dev.jinihotel.MainScreen.mainHotelName;


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




    public upcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        upcomingList=view.findViewById(R.id.upcomingList);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(mainHotelName).child("Bookings");

        adapter=new bookingsAdapter(getActivity(),R.layout.booked_items_row,getters);
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

        return view;



    }
}
