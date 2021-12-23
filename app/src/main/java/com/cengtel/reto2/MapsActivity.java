package com.cengtel.reto2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cengtel.reto2.datos.DBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.cengtel.reto2.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final int REQUEST_CODE_GALLERY = 999;
    private Button btnSucInsertar, btnSucConsultar, btnSucEliminar, btnSucActualizar, btnSucChoose;
    private EditText editSucId, editSucName, editSucDescription, editSucFavorite;
    private ImageView imgSucursal;
    private TextView location;
    String name = "";
    private DBHelper dbHelper;

    String campo1SucInsert, campo2SucInsert, campo3SucInsert, campo4SucInsert;
    byte[] imgSucInsert;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        name = i.getStringExtra("name");

        btnSucInsertar = (Button) findViewById(R.id.btn_sucursal_insert);
        btnSucConsultar = (Button) findViewById(R.id.btn_sucursal_consultar);
        btnSucEliminar = (Button) findViewById(R.id.btn_sucursal_borrar);
        btnSucActualizar = (Button) findViewById(R.id.btn_sucursal_actualizar);
        btnSucChoose = (Button) findViewById(R.id.btn_sucursal_select);
        imgSucursal = (ImageView) findViewById(R.id.img_sucursal);
        dbHelper = new DBHelper(getApplicationContext());

        editSucId = (EditText) findViewById(R.id.edit_sucursal_id);
        editSucName = (EditText) findViewById(R.id.edit_sucursal_campo1);
        editSucDescription = (EditText) findViewById(R.id.edit_sucursal_campo2);
        editSucFavorite = (EditText) findViewById(R.id.edit_sucursal_campo4);
        location = (TextView) findViewById(R.id.edit_sucursal_campo3);

        btnSucChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MapsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnSucInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarCampos();
                dbHelper.insertData(campo1SucInsert, campo2SucInsert, campo3SucInsert, campo4SucInsert, imgSucInsert, name);
                limpiarCampos();
                Toast.makeText(getApplicationContext(), "Agregado",Toast.LENGTH_SHORT).show();

            }
        });

        btnSucConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getDataById(name, editSucId.getText().toString().trim());
                while (cursor.moveToNext()){
                    editSucName.setText(cursor.getString(1));
                    editSucDescription.setText(cursor.getString(2));
                    location.setText(cursor.getString(3));
                    editSucFavorite.setText(cursor.getString(4));
                    byte[] img = cursor.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    imgSucursal.setImageBitmap(bitmap);
                }
            }
        });

        btnSucActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    llenarCampos();
                    dbHelper.updateSucursalById(name, editSucId.getText().toString().trim(),
                            campo1SucInsert, campo2SucInsert, campo3SucInsert, campo4SucInsert,imgSucInsert);
                    limpiarCampos();
            }
        });

        btnSucEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutAgregar = (LinearLayout) findViewById(R.id.Linear_layout_agregar);
                dbHelper.deleteDataById(name, editSucId.getText().toString().trim());
                limpiarCampos();
                Snackbar.make(linearLayoutAgregar, "Eliminado", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void llenarCampos(){
        campo1SucInsert = editSucName.getText().toString().trim();
        campo2SucInsert = editSucDescription.getText().toString().trim();
        campo3SucInsert = location.getText().toString().trim();
        campo4SucInsert = editSucFavorite.getText().toString().trim();
        imgSucInsert = imageViewToByte(imgSucursal);
    }

    public void limpiarCampos(){
        editSucName.setText("");
        editSucDescription.setText("");
        location.setText("");
        editSucFavorite.setText("");
        imgSucursal.setImageResource(R.mipmap.ic_launcher);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng aeropuerto = new LatLng(4.7010,-74.1461);
        mMap.addMarker(new MarkerOptions().position(aeropuerto).title("Marker in El dorado"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(aeropuerto));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aeropuerto,15));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                location.setText(latLng.latitude+","+latLng.longitude);
                //Toast.makeText(getApplicationContext(), latLng.latitude+" "+latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "Sin permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSucursal.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}