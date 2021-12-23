package com.cengtel.reto2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cengtel.reto2.modelos.Sucursal;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.cengtel.reto2.databinding.ActivityMostrarMapsBinding;

public class MostrarMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMostrarMapsBinding binding;
    private Sucursal sucursal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMostrarMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        String location = i.getStringExtra("location");
        String name = i.getStringExtra("name");
        sucursal = new Sucursal(name, location);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng init = sucursal.locationToCoord();
        mMap.addMarker(new MarkerOptions().position(init).title(sucursal.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init,15));
    }
}