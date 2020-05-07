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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class hotelPicture extends AppCompatActivity {

    private static int PICK_IMAGE=101;
    Button addShopPicButton;
    TextView info;
    ArrayList<Uri> imageList=new ArrayList<>();
    Uri imageUri;
    FirebaseStorage storage;
    String shopPicLinks="";
    boolean isPicUploaded=false;
    Button addPicButton;

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
        setContentView(R.layout.activity_hotel_picture);

        addShopPicButton=findViewById(R.id.uploadShopPic);
        info=findViewById(R.id.infoPic);
        storage=FirebaseStorage.getInstance();
        info.setVisibility(View.INVISIBLE);
       addPicButton=findViewById(R.id.addPicsBtn);

        addShopPicButton.setEnabled(false);


        addPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
                    choosePhoto(PICK_IMAGE);
                }
            }
        });

        addShopPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0;i<imageList.size();i++){

                    Uri individualImage=imageList.get(i);
                    String randomUUID= UUID.randomUUID().toString()+".jpeg";
                    final StorageReference ref=storage.getReference().child("hotelPics").child(Hotel_Info.nameHotel).child("HotelPics").child(randomUUID);

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
                                    isPicUploaded=true;
                                    FirebaseDatabase.getInstance().getReference().child("Hotels").child(Hotel_Info.nameHotel).child("Info").child("picLinks").setValue(shopPicLinks);
                                }
                            });
                            overridePendingTransition(0,0);
                            startActivity(new Intent(getApplicationContext(),MainScreen.class));
                            overridePendingTransition(0,0);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                if(imageList.size()>=1) {

                    info.setText("Pictures Uploaded Successfully");
                    addShopPicButton.setText("Uploaded Successfully");
                    addShopPicButton.setEnabled(false);
                    info.setVisibility(View.VISIBLE);

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
                            info.setText("");
                        }
                        addShopPicButton.setEnabled(true);
                        addPicButton.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(this, "Please Select An Image", Toast.LENGTH_SHORT).show();
                        if (requestCode == PICK_IMAGE) {
                            info.setText("Please Select An Image");
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
