package firefly.dev.jiniuser;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class bookingsAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<bookedRoomsGetter> getters;
    ArrayList<String> hotelNames;

    public bookingsAdapter(@NonNull Context context, int resource, ArrayList<bookedRoomsGetter> getters, ArrayList<String> hotelNames) {
        super(context, resource, getters);
        this.context=context;
        this.getters=getters;
        this.resource=resource;
        this.hotelNames=hotelNames;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        ImageView userImage=view.findViewById(R.id.userImage);
        TextView username=view.findViewById(R.id.hotelName);
        TextView checkinDate=view.findViewById(R.id.checkinDate);
        TextView roomTypeBooked=view.findViewById(R.id.roomTypeBooked);
        TextView stayDays=view.findViewById(R.id.stayDays);
        TextView checkedInTime=view.findViewById(R.id.checkedInTime);

        username.setText(hotelNames.get(position));
        checkedInTime.setText(getters.get(position).getTimeIn());
        checkinDate.setText(getters.get(position).getBookDate());
        roomTypeBooked.setText(getters.get(position).getRoomType());
        stayDays.setText(getters.get(position).getStayDays());

        ArrayList<Integer> ImgArray=new ArrayList<>();
        ImgArray.add(R.drawable.hotel1);
        ImgArray.add(R.drawable.hotel2);
        ImgArray.add(R.drawable.hotel3);
        ImgArray.add(R.drawable.hotel4);
        ImgArray.add(R.drawable.hotel5);

        Random rand = new Random();
        int randomElement = ImgArray.get(rand.nextInt(ImgArray.size()));

        Drawable myDrawable = context.getResources().getDrawable(randomElement);

        userImage.setImageDrawable(myDrawable);




        return view;
    }





}
