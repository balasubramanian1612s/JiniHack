package firefly.dev.jinihotel.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import firefly.dev.jinihotel.R;
import firefly.dev.jinihotel.getters.bookedRoomsGetter;

public class bookingsAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<bookedRoomsGetter> getters;

    public bookingsAdapter(@NonNull Context context, int resource, ArrayList<bookedRoomsGetter> getters) {
        super(context, resource, getters);
        this.context=context;
        this.getters=getters;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        ImageView userImage=view.findViewById(R.id.userImage);
        TextView username=view.findViewById(R.id.username);
        TextView checkinDate=view.findViewById(R.id.checkinDate);
        TextView roomTypeBooked=view.findViewById(R.id.roomTypeBooked);
        TextView stayDays=view.findViewById(R.id.stayDays);
        TextView checkedInTime=view.findViewById(R.id.checkedInTime);

        username.setText(getters.get(position).getUsername());
        checkedInTime.setText(getters.get(position).getTimeIn());
        checkinDate.setText(getters.get(position).getBookDate());
        roomTypeBooked.setText(getters.get(position).getRoomType());
        stayDays.setText(getters.get(position).getStayDays());

        Picasso.get().load(getters.get(position).getCurrentPhoto()).into(userImage);




        return view;
    }





}
