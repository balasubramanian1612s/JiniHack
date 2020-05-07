package firefly.dev.jiniuser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class hotelListAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<hotelInfoGetter> getter;

    public hotelListAdapter(@NonNull Context context, int resource,ArrayList<hotelInfoGetter> getter) {
        super(context, resource, getter);
        this.resource=resource;
        this.context=context;
        this.getter=getter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);
        ImageView hotelImageView;
        TextView hotelNameTv,hotelAddressTv,hotelRating;
        hotelImageView=view.findViewById(R.id.hotelImageView);
        hotelAddressTv=view.findViewById(R.id.hotelAddressTv);
        hotelNameTv=view.findViewById(R.id.hotelNameTv);
        hotelRating=view.findViewById(R.id.hotelRating);

        hotelAddressTv.setText(getter.get(position).getHotelAddress());
        hotelNameTv.setText(getter.get(position).getHotelName());
        hotelRating.setText("⭐"+getter.get(position).getRating());


        hotelImageView.setImageResource(R.drawable.hotel1);


       /* String url=getter.get(position).getPicLinks();
            url=url.substring(0,url.length()-1);
            String[] photos = url.split(",");

            Picasso.with(context).load(photos[1]).into(hotelImageView);*/



        return view;
    }
}
