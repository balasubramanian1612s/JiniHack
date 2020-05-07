package firefly.dev.jinihotel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import firefly.dev.jinihotel.R;

public class hotelRoomAdapter extends ArrayAdapter {

    ArrayList<String> tarrifsArray;
    ArrayList<String> roomTypeArray;
    Context context;
    int resource;

    public hotelRoomAdapter(@NonNull Context context, int resource, ArrayList<String> tarrifsArray,ArrayList<String> roomTypeArray) {
        super(context, resource, tarrifsArray);
        this.roomTypeArray=roomTypeArray;
        this.tarrifsArray=tarrifsArray;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView hotelTarrifTv,hotelNameTv;
        hotelNameTv=view.findViewById(R.id.hotelNameTv);
        hotelTarrifTv=view.findViewById(R.id.hotelTarrifTv);

        hotelNameTv.setText(roomTypeArray.get(position));
        hotelTarrifTv.setText(tarrifsArray.get(position));



        return view;

    }
}
