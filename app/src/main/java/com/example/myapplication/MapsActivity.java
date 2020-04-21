package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ImageView mWalkMode;
    private ImageView mDrivingMode;
    private ImageView mTransitMode;
    private ImageView mClearMap;

    double latitude, longitude;
    double end_latitude, end_longitude;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mWalkMode = findViewById(R.id.walkingMode);
        mDrivingMode = findViewById(R.id.drivingMode);
        mTransitMode = findViewById(R.id.transitMode);
        mClearMap = findViewById(R.id.clearMap);

        getLocationPermission();
        getDeviceLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(mLocationPermissionsGranted){
            if(ActivityCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private void GeoLocate(){
        String address;
        if(getIntent().hasExtra("bar_location")){
            address = getIntent().getStringExtra("bar_location");

            Geocoder geocoder = new Geocoder(MapsActivity.this);
            List<Address> list = new ArrayList<>();
            MarkerOptions markerOptions = new MarkerOptions();

            try {
                list = geocoder.getFromLocationName(address, 1);
            }
            catch(IOException e){
                Log.e("Tag", "geoLocate: IOException: " + e.getMessage());
            }
            if(list.size() > 0){
                Address geoAddress = list.get(0);
                moveCamera(new LatLng(geoAddress.getLatitude(), geoAddress.getLongitude()), 15f);
                LatLng addressLatlng = new LatLng(geoAddress.getLatitude(), geoAddress.getLongitude());
                markerOptions.position(addressLatlng);
                markerOptions.title(geoAddress.getAddressLine(0));

                // My home co-ordinates. Co-ordinates with this virtual device are in America, which causes the map to crash
                // when attempting to get directions from there to a bar in Ireland. These hard coded numbers would be taken out for release.

                latitude = 53.3418053;
                longitude = -6.2359087;

                end_latitude = geoAddress.getLatitude();
                end_longitude = geoAddress.getLongitude();

                Object dataTransfer[] = new Object[3];
                String url = getDirectionsUrl(mode);

                GetDirectionsDataMaps getDirectionsDataMaps = new GetDirectionsDataMaps();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = mode;
                getDirectionsDataMaps.execute(dataTransfer);

            }
            mMap.addMarker(markerOptions);


        }
    }

    private String getDirectionsUrl(int mode)
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        if(mode == 1) {
            googleDirectionsUrl.append("&mode=walking");
        }
        else if(mode == 2){
            googleDirectionsUrl.append("&mode=driving");
        }
        else{
            googleDirectionsUrl.append("&mode=transit");
        }
        googleDirectionsUrl.append("&key="+"AIzaSyDPCQfEEedrtowrXM_DSjNOrf5Y-LSh2Es");


        return googleDirectionsUrl.toString();
    }


    private void getDeviceLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if (mLocationPermissionsGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Location currentLocation = (Location) task.getResult();
                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();
                        moveCamera(new LatLng(latitude, longitude), 15f);
                    }
                });
            }
        }catch(SecurityException e){
            Log.e("Tag","getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mWalkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;
                GeoLocate();
            }
        });
        mDrivingMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                GeoLocate();
            }
        });
        mTransitMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 3;
                GeoLocate();
            }
        });
        mClearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });
    }

    private void getLocationPermission(){
        String[] permissions = {FINE_LOCATION,COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            mLocationPermissionsGranted = false;

            switch(requestCode){
                case LOCATION_PERMISSION_REQUEST_CODE:{
                    if(grantResults.length > 0){
                        for(int i = 0; i < grantResults.length; i++){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                mLocationPermissionsGranted = false;
                                return;
                            }
                        }
                        mLocationPermissionsGranted = true;
                        initMap();
                    }
                }
            }
    }

}
