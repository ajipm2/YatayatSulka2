package com.yatayatsulka;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<MapLatLongResponseModel> responseModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        getLatitudeLongitude();

    }

    private void getLatitudeLongitude() {
        responseModels = new ArrayList<>();
        addLatLong();

    }

    private void addLatLong() {
        addMarker(27.667530, 85.322000);//Lagankhel
        addMarker(27.687839, 85.316298);//Kupondle
        addMarker(27.702497, 85.313493);//NAC
        addMarker(27.708850, 85.315315);//Jamal
        addMarker(27.717135, 85.315073);//Lainchaur
        addMarker(27.732426, 85.308128);//Naya buspark
        addMarker(27.701073, 85.353376);//Airport
        addMarker(27.704176, 85.316446);//Purano BusPark
        addMarker(27.699683, 85.314085);//Sahid Gate
        addMarker(27.725081, 85.298170);//Banasthali
        addMarker(27.678226, 85.348887);//Koteshwor
        addMarker(27.688604, 85.334718);//Naya Baneshwor
        addMarker(27.694059, 85.319379);//Thapathali
        addMarker(27.693883, 85.313000);//Tripureshwor
        addMarker(27.693948, 85.281477);//Kalanki
        addMarker(27.716648, 85.283389);//Swayambhu
        addMarker(27.666886, 85.332121);//Gwarko
        addMarker(27.756269, 85.349028);//Hattigauda
    }

    private void addMarker(double latitude, double longitude) {
            LatLng kathmandu = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(kathmandu)
                    .title("kathmandu city")
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kathmandu, 12));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(MapsActivity.this,"MarkerClicked",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

}
