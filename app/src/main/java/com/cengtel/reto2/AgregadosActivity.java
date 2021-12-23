package com.cengtel.reto2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cengtel.reto2.datos.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureClassLoader;
import java.util.HashMap;

public class AgregadosActivity extends AppCompatActivity {
    private final int REQUEST_CODE_GALLERY = 999;
    private TextView title;
    private EditText campo1, campo2, campo3, campo4, editId;
    private Button btnChoose, btnInsertar, btnConsultar, btnActualizar, btnBorrar;
    private ImageView imgSelected;
    String name = "";
    private DBHelper dbHelper;

    String campo1Insert, campo2Insert, campo3Insert, campo4Insert;
    byte[] imgInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregados);

        title = (TextView) findViewById(R.id.tv_titulo);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        campo1 = (EditText) findViewById(R.id.editCampo1);
        campo2 = (EditText) findViewById(R.id.editCampo2);
        campo3 = (EditText) findViewById(R.id.editCampo3);
        campo4 = (EditText) findViewById(R.id.editCampo4);
        editId = (EditText) findViewById(R.id.editId);
        btnChoose = (Button) findViewById(R.id.btn_choose);
        btnInsertar = (Button) findViewById(R.id.btn_insertar);
        btnConsultar = (Button) findViewById(R.id.btn_consultar);
        btnActualizar = (Button) findViewById(R.id.btn_actualizar);
        btnBorrar = (Button) findViewById(R.id.btn_eliminar);
        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        dbHelper = new DBHelper(getApplicationContext());



        title.setText(name);

        if(name.equals("PRODUCTOS")){
            campo1.setHint("Nombre");
            campo2.setHint("Descripcion");
            campo3.setHint("Precio");
            campo4.setHint("Favorito 0->No 1->Si");

        } else if(name.equals("SERVICIOS")){
            campo1.setHint("Nombre");
            campo2.setHint("Descripcion");
            campo3.setHint("Precio");
            campo4.setHint("Favorito 0->No 1->Si");

        } else if(name.equals("SUCURSALES")){
            campo1.setHint("Nombre");
            campo2.setHint("Descripcion");
            campo3.setHint("Ubicacion");
            campo4.setHint("Favorito 0->No 1->Si");
            campo3.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AgregadosActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    llenarCampos();
                    dbHelper.insertData(campo1Insert, campo2Insert, campo3Insert, campo4Insert, imgInsert, name);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Agregado",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getDataById(name, editId.getText().toString().trim());
                while (cursor.moveToNext()){
                    campo1.setText(cursor.getString(1));
                    campo2.setText(cursor.getString(2));
                    campo3.setText(cursor.getString(3));
                    campo4.setText(cursor.getString(4));
                    byte[] img = cursor.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    imgSelected.setImageBitmap(bitmap);
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.equals("PRODUCTOS")){
                    llenarCampos();
                    dbHelper.updateProductoById(name, editId.getText().toString().trim(), campo1Insert,
                    campo2Insert, campo3Insert, campo4Insert, imgInsert);
                    limpiarCampos();

                } else if(name.equals("SERVICIOS")){
                    llenarCampos();
                    dbHelper.updateServicioById(name, editId.getText().toString().trim(), campo1Insert,
                            campo2Insert, campo3Insert, campo4Insert, imgInsert);
                    limpiarCampos();

                } else if(name.equals("SUCURSALES")){
                    llenarCampos();
                    dbHelper.updateSucursalById(name, editId.getText().toString().trim(), campo1Insert,
                            campo2Insert, campo3Insert, campo4Insert, imgInsert);
                    limpiarCampos();
                }

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutAgregar = (LinearLayout) findViewById(R.id.Linear_layout_agregar);
                dbHelper.deleteDataById(name, editId.getText().toString().trim());
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
        campo1Insert = campo1.getText().toString().trim();
        campo2Insert = campo2.getText().toString().trim();
        campo3Insert = campo3.getText().toString().trim();
        campo4Insert = campo4.getText().toString().trim();
        imgInsert = imageViewToByte(imgSelected);
        System.out.println("test");
    }

    public void limpiarCampos(){
        campo1.setText("");
        campo2.setText("");
        campo3.setText("");
        campo4.setText("");
        imgSelected.setImageResource(R.mipmap.ic_launcher);
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
                imgSelected.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}