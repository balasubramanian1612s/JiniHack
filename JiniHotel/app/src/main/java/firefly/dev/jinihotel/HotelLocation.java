package firefly.dev.jinihotel;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HotelLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    Location userLoc;
    SearchView searchView;
    Button setLoc;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    userLoc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    if (userLoc == null) {
                        Toast.makeText(this, "The Location Services are turned off please select a location on map or search fir a location", Toast.LENGTH_SHORT).show();
                    } else {
                        LatLng pos = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                        Log.i("User Location", userLoc.toString());
                        Marker m = mMap.addMarker(new MarkerOptions().position(pos).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f));
                    }
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchView = findViewById(R.id.searchLoc);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mMap.clear();
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {

                    Geocoder geocoder = new Geocoder(HotelLocation.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Log.i("address", addressList.get(0) + "");

                    if (!addressList.isEmpty()) {

                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
                        Location loc = new Location(LocationManager.GPS_PROVIDER);
                        loc.setLatitude(latLng.latitude);
                        loc.setLongitude(latLng.longitude);
                        userLoc = loc;

                    } else {
                        Toast.makeText(HotelLocation.this, "Search for the location with a little more detail", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if(userLoc!=null) {
                                userLoc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                                LatLng pos = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(pos).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f));
                            }
                        }
                    }



                }
                return false;
            }





            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        setLoc=findViewById(R.id.setLocBtn);

        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "This version of android does not support these services", Toast.LENGTH_SHORT).show();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            } else {
                userLoc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                if (userLoc == null) {
                    Toast.makeText(this, "The Location Services are turned off please select a location on map or search for a location", Toast.LENGTH_SHORT).show();
                } else {
                    userLoc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(userLoc!=null) {
                        LatLng pos = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                        Log.i("User Location", userLoc.toString());
                        mMap.addMarker(new MarkerOptions().position(pos).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f));
                    }
                }
            }
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
                Location loc=new Location(LocationManager.GPS_PROVIDER);
                loc.setLatitude(latLng.latitude);
                loc.setLongitude(latLng.longitude);
                userLoc=loc;
            }
        });

        setLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLoc!=null) {
                    String lat = userLoc.getLatitude() + "";
                    String lon = userLoc.getLongitude() + "";
                    FirebaseDatabase.getInstance().getReference().child(Hotel_Info.nameHotel).child("Info").child("lat").setValue(lat);
                    FirebaseDatabase.getInstance().getReference().child(Hotel_Info.nameHotel).child("Info").child("lon").setValue(lon);

                    startActivity(new Intent(getApplicationContext(),hotelPicture.class));

                }
            }
        });
    }
}
