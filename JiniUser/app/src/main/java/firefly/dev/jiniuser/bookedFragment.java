package firefly.dev.jiniuser;

import android.app.Fragment;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import static firefly.dev.jiniuser.bookings.topBarContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class bookedFragment extends Fragment {

    ListView bookedList;
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



    public bookedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booked, container, false);
        bookedList=view.findViewById(R.id.bookedList);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("My Bookings");

        bookedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(topBarContext,UserServices.class);
                intent.putExtra("HotelName",getters.get(i).getHotel());
                startActivity(intent);


            }
        });

        adapter=new bookingsAdapter(getActivity(),R.layout.booked_items_row,getters,hotelNames);
        bookedList.setAdapter(adapter);
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
                if (date1.before(date)){


                    getters.add(newGetter);
                    hotelNames.add(newGetter.getHotel());
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
