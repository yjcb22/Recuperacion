package com.cengtel.reto2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String nameDet = "";
    String idDet = "";
    String descDet = "";
    String priceDet = "";
    String favDet = "";

    TextView tvNameDet, tvIdDet, tvDescDet, tvPriceDet;
    ImageView imageFavoriteDet, imageDet;
    byte[] imgByte;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();

        idDet = i.getStringExtra("identificador");
        nameDet = i.getStringExtra("name");
        descDet = i.getStringExtra("description");
        priceDet = i.getStringExtra("price");
        favDet = i.getStringExtra("favorite");
        imgByte = i.getByteArrayExtra("img");


        tvNameDet = (TextView) findViewById(R.id.title_det);
        tvIdDet = (TextView) findViewById(R.id.id_det);
        tvDescDet = (TextView) findViewById(R.id.description_det);
        tvPriceDet = (TextView) findViewById(R.id.price_det);
        imageFavoriteDet = (ImageView) findViewById(R.id.favorite_det);
        imageDet = (ImageView) findViewById(R.id.image_det);

        tvNameDet.setText(nameDet);
        tvIdDet.setText(idDet);
        tvDescDet.setText(descDet);
        tvPriceDet.setText(priceDet);

        imageFavoriteDet = (ImageView) findViewById(R.id.favorite_det);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        imageDet.setImageBitmap(bitmap);

        if(favDet.equals("1")){
            imageFavoriteDet.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.third));
        }else if(favDet.equals("0")){
            imageFavoriteDet.setColorFilter(Color.argb(128, 128, 128, 128));
        }






    }
}