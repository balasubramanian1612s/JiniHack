package firefly.dev.jiniuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodListAdapter extends ArrayAdapter<FoodsGetter> {
    Context context;
    int resource;
    ArrayList<FoodsGetter> getter;

    public FoodListAdapter(@NonNull Context context, int resource, ArrayList<FoodsGetter> getter) {
        super(context, resource, getter);
        this.resource=resource;
        this.context=context;
        this.getter=getter;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(resource, null, false);
        final TextView foodName=view.findViewById(R.id.foodNameTw);
        TextView foodDescription=view.findViewById(R.id.foodDescriptionTw);
        TextView foodPrice=view.findViewById(R.id.foodPriceTw);
        final Button orderBtn=view.findViewById(R.id.orderBtn);
        final Button deleteOrderBtn=view.findViewById(R.id.deleteOrderBtn);

        foodName.setText("* "+getter.get(position).getFoodName());
        foodDescription.setText(getter.get(position).getDescription());
        foodPrice.setText("Rs. "+getter.get(position).getPrice());



        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FoodDelivery.orders.contains(getter.get(position).getFoodName())) {
                    FoodDelivery.orders.add(getter.get(position).getFoodName());
                    Toast.makeText(context, "Ordered " + getter.get(position).getFoodName(), Toast.LENGTH_SHORT).show();
                    deleteOrderBtn.setVisibility(View.VISIBLE);
                    orderBtn.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(context, "Already Ordered !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(FoodDelivery.orders.contains(getter.get(position).getFoodName())){
                   FoodDelivery.orders.remove(getter.get(position).getFoodName());
                   deleteOrderBtn.setVisibility(View.INVISIBLE);
                   orderBtn.setVisibility(View.VISIBLE);
                   Toast.makeText(context, "Deleted "+getter.get(position).getFoodName(), Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(context, "Nothing to delete", Toast.LENGTH_SHORT).show();
               }

            }
        });

        return view;
    }
}
