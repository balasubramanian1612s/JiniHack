package firefly.dev.jiniuser;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class bookingPage extends AppCompatActivity {

    Button bookConfirm;
    FirebaseDatabase database;
    DatabaseReference reference, reffAuth;
    private static int PICK_IMAGE=101;

    private static int PICK_DOC=202;

    ArrayList<Uri> imageList=new ArrayList<>();
    Uri imageUri;
    FirebaseStorage storage;

    String shopPicLinks="";

    String docPicLinks="";
    Button addShopPicButton;
    Button addProofButton;
    Button uploadProofButton;
    Button uploadPicButton;

    boolean isDocUploaded=false;
    boolean isPicUploaded=false;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                choosePhoto(PICK_IMAGE);

            }
        }else if(requestCode==2){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                choosePhoto(PICK_DOC);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        final Intent intent=getIntent();
        final String roomName=intent.getStringExtra("RoomName");
        final String hotelName=intent.getStringExtra("HotelName");
        final String uidr=intent.getStringExtra("uidr");
        final String uidauth=intent.getStringExtra("uidauth");
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Hotels").child(hotelName).child("Bookings");
        reffAuth=database.getReference().child("User").child(FirebaseAuth.getInstance().getUid());
        addProofButton=findViewById(R.id.addIdProofBtn);
        addShopPicButton=findViewById(R.id.addPhotoBtn);
        uploadPicButton=findViewById(R.id.uploadPictureBtn);
        uploadProofButton=findViewById(R.id.uploadProofBtn);
        storage=FirebaseStorage.getInstance();

        Random r=new Random();
        int num1=r.nextInt((9999-1000))+1000;
        int num2=r.nextInt((9999-1000))+1000;

        final String userNameCred="jini"+num1;
        final String passW=num2+"";


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.homeMenu:
                        Toast.makeText(bookingPage.this, "Sorry! You Can't Switch Tab Over Here!", Toast.LENGTH_SHORT).show();


                    case R.id.orderMenu:
                        Toast.makeText(bookingPage.this, "Sorry! You Can't Switch Tab Over Here!", Toast.LENGTH_SHORT).show();



                }


                return false;
            }
        });


        bookConfirm=findViewById(R.id.bookConfirm);
        bookConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPicUploaded == true && isDocUploaded == true) {

                    reference = reference.child(uidr);
                    reference.child("UserId").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child("Username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    reference.child("RoomType").setValue(roomName);
                    reference.child("IDProof").setValue(docPicLinks);
                    reference.child("CurrentPhoto").setValue(shopPicLinks);
                    reference.child("usernameCred").setValue(userNameCred);
                    reference.child("pass").setValue(passW);
                    reffAuth = reffAuth.child("My Bookings").child(uidauth);
                    reffAuth.child("Hotel").setValue(hotelName);
                    reffAuth.child("RoomNo").setValue("123");
                    reffAuth.child("RoomType").setValue(roomName);
                    reffAuth.child("PaymentStatus").setValue("None");
                    reffAuth.child("usernameCred").setValue(userNameCred);
                    reffAuth.child("pass").setValue(passW);
                    Intent intent1=new Intent(bookingPage.this,booking_bill.class);
                    startActivity(intent1);

                } else {
                    Toast.makeText(bookingPage.this, "Please Upload all the documents", Toast.LENGTH_SHORT).show();
                }
            }

        });


        addShopPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }else{
                    choosePhoto(PICK_DOC);
                }
            }
        });

        uploadPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0;i<imageList.size();i++){

                    Uri individualImage=imageList.get(i);
                    String randomUUID= UUID.randomUUID().toString()+".jpeg";
                    final StorageReference ref=storage.getReference().child("userPics").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userSelfPic").child(randomUUID);

                    ref.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.i("Upload","Successful");

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url=String.valueOf(uri);
                                    shopPicLinks=shopPicLinks+url+",";
                                    Log.i("Link",url);
                                    isPicUploaded=true;
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
                    addShopPicButton.setText("");
                    addShopPicButton.setEnabled(false);
                    addShopPicButton.setVisibility(View.INVISIBLE);
                    addShopPicButton.setVisibility(View.INVISIBLE);
                    uploadPicButton.setEnabled(false);



                }



            }
        });

        addProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked","Now");
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                }else{
                    choosePhoto(PICK_DOC);
                }
            }

        });

        uploadProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<imageList.size();i++){

                    Uri individualImage=imageList.get(i);
                    String randomUUID= UUID.randomUUID().toString()+".jpeg";
                    final StorageReference ref=storage.getReference().child("userPics").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userProofPic").child(randomUUID);

                    ref.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.i("Upload","Successful");

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url=String.valueOf(uri);
                                    docPicLinks=docPicLinks+url+",";
                                    Log.i("Link",url);
                                    isDocUploaded=true;
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
                    addProofButton.setText("");
                    addProofButton.setEnabled(false);
                    uploadProofButton.setEnabled(false);
                    addProofButton.setVisibility(View.INVISIBLE);
                    addShopPicButton.setEnabled(true);
                    uploadPicButton.setEnabled(true);


                }

            }

        });


    }
    public void choosePhoto(int requestCode){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//preinstalled android activity
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);//sending if multiple selections are possible ,to the activity
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE || requestCode == PICK_DOC) {
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

                    } else {
                        Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "You Can't Go Back! YOu started Bookinig the hotel!", Toast.LENGTH_SHORT).show();

    }
}
