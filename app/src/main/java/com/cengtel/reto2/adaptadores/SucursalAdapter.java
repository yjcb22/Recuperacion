package com.cengtel.reto2.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cengtel.reto2.MapsActivity;
import com.cengtel.reto2.MostrarMapsActivity;
import com.cengtel.reto2.R;
import com.cengtel.reto2.modelos.Sucursal;


import java.util.ArrayList;

public class SucursalAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sucursal> sucursales;
    LayoutInflater inflater;

    public SucursalAdapter(Context context, ArrayList<Sucursal> sucursales) {
        this.context = context;
        this.sucursales = sucursales;
    }

    @Override
    public int getCount() {
        return sucursales.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_sucursal_item,null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgS1);
        TextView campo1 = convertView.findViewById(R.id.tv_suc_campo1);
        TextView campo2 = convertView.findViewById(R.id.tv_suc_campo2);
        TextView campo3 = convertView.findViewById(R.id.tv_suc_campo3);
        ImageView imageFavorite = convertView.findViewById(R.id.img_favorite);

        TextView tvId = convertView.findViewById(R.id.tv_suc_id);

        Sucursal sucursal = sucursales.get(position);
        byte[] image = sucursal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        tvId.setText("ID: " + sucursal.getId());
        campo1.setText(sucursal.getName());
        campo2.setText(sucursal.getDescription());
        campo3.setText(sucursal.getLocation());

        if(sucursal.getFavorite().equals("1")){
            imageFavorite.setColorFilter(ContextCompat.getColor(context, R.color.third));
        }else if(sucursal.getFavorite().equals("0")){
            imageFavorite.setColorFilter(Color.argb(128, 128, 128, 128));
        }

        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MostrarMapsActivity.class);
                i.putExtra("name", sucursal.getName());
                i.putExtra("location", sucursal.getLocation());
                context.startActivity(i);
            }
        });


        return convertView;
    }
}
