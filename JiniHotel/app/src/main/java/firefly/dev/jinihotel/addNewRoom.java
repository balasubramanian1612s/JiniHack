package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import firefly.dev.jinihotel.getters.AmenitiesGetter;

import static firefly.dev.jinihotel.MainScreen.mainHotelName;

public class addNewRoom extends AppCompatActivity {

    private static int PICK_IMAGE=101;
    EditText roomName,tarrif,bedType,roomSize;
    Spinner SpinWifi,SpinLaundry,SpinPool;
    Button addRoomdata;
    CheckBox dinnerCB,breakfastCB,lunchCB,brunchCB,eveningSnackCB,noComplementryCB;
    String availability[]={"Yes","No"};
    FirebaseDatabase database;
    DatabaseReference reference;
    Button addImage;
    Button uploadImage;
    ArrayList<Uri> imageList=new ArrayList<>();
    Uri imageUri;
    FirebaseStorage storage;
    String shopPicLinks="";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                choosePhoto(PICK_IMAGE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_room);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(mainHotelName).child("Rooms");


        roomName=findViewById(R.id.roomName);
        roomSize=findViewById(R.id.roomSize);
        tarrif=findViewById(R.id.tarrif);
        bedType=findViewById(R.id.bedType);
        SpinWifi=findViewById(R.id.SpinWifi);
        SpinLaundry=findViewById(R.id.SpinLaundry);
        SpinPool=findViewById(R.id.SpinPool);
        dinnerCB=findViewById(R.id.dinnerCB);
        noComplementryCB=findViewById(R.id.noComplementryCB);
        breakfastCB=findViewById(R.id.breakfastCB);
        lunchCB=findViewById(R.id.lunchCB);
        brunchCB=findViewById(R.id.brunchCB);
        eveningSnackCB=findViewById(R.id.eveningSnackCB);
        addRoomdata=findViewById(R.id.addRoomdata);
        addImage=findViewById(R.id.addImage);
        uploadImage=findViewById(R.id.add360image);

        storage=FirebaseStorage.getInstance();

        ArrayAdapter spinAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,availability);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinPool.setAdapter(spinAdapter);
        SpinLaundry.setAdapter(spinAdapter);
        SpinWifi.setAdapter(spinAdapter);

        addRoomdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference=reference.child(roomName.getText().toString());
                reference.child("Tarrif").setValue(tarrif.getText().toString());
                reference=reference.child("Amenities");

                String com="";
                if (dinnerCB.isChecked()){

                    com=com+"DCB,";

                }
                if (breakfastCB.isChecked()){

                    com=com+"BCB,";

                }
                if (brunchCB.isChecked()){

                    com=com+"BRCB,";

                }
                if (lunchCB.isChecked()){

                    com=com+"LCB,";

                }
                if (eveningSnackCB.isChecked()){

                    com=com+"ESCB,";

                }
                if (noComplementryCB.isChecked()){

                    com=com+"No Complementry";

                }
                com=com.substring(0,com.length()-1);


                AmenitiesGetter getter=new AmenitiesGetter(com,SpinLaundry.getSelectedItem().toString(),SpinWifi.getSelectedItem().toString(),SpinPool.getSelectedItem().toString(), bedType.getText().toString(),roomSize.getText().toString());
                reference.setValue(getter);

                Intent intent=new Intent(addNewRoom.this,MainScreen.class);
                startActivity(intent);

            }
        });

        uploadImage.setEnabled(false);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
                    choosePhoto(PICK_IMAGE);
                }
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0;i<imageList.size();i++){

                    Uri individualImage=imageList.get(i);
                    String randomUUID= UUID.randomUUID().toString()+".jpeg";
                    final StorageReference ref=storage.getReference().child("hotelPics").child(mainHotelName).child("RoomPics").child(roomName.getText().toString()).child(randomUUID);

                    ref.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.i("Upload","Successful");

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url=String.valueOf(uri);
                                    shopPicLinks=shopPicLinks+url+",";
                                    Log.i("ShopLink",shopPicLinks);
                                    Log.i("Link",url);
                                    FirebaseDatabase.getInstance().getReference().child("Hotels").child(mainHotelName).child("Rooms").child(roomName.getText().toString()).child("picLinks").setValue(shopPicLinks);
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                if(imageList.size()>=1) {

                    //info.setText("Pictures Uploaded Successfully");
                    uploadImage.setText("Uploaded Successfully");
                    uploadImage.setEnabled(false);
                    //info.setVisibility(View.VISIBLE);

                }



            }
        });



            }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            imageList.clear();
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    if (data.getClipData().getItemCount() >= 1) {

                        int countClipData = data.getClipData().getItemCount();

                        int currentImageSelected = 0;

                        while (currentImageSelected < countClipData) {

                            imageUri = data.getClipData().getItemAt(currentImageSelected).getUri();

                            imageList.add(imageUri);

                            currentImageSelected++;
                        }
                        if (requestCode == PICK_IMAGE) {
                            //info.setText("");
                        }
                        uploadImage.setEnabled(true);
                        addImage.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(this, "Please Select An Image", Toast.LENGTH_SHORT).show();
                        if (requestCode == PICK_IMAGE) {
                            //info.setText("Please Select An Image");
                        }
                    }
                }
            }
        }
    }
    public void choosePhoto(int requestCode){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//preinstalled android activity
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);//sending if multiple selections are possible ,to the activity
        startActivityForResult(intent,requestCode);
    }
}
