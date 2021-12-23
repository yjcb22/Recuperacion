package com.cengtel.reto2.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.cengtel.reto2.DetailsActivity;
import com.cengtel.reto2.MostrarMapsActivity;
import com.cengtel.reto2.R;
import com.cengtel.reto2.modelos.Producto;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Producto> productos;
    LayoutInflater inflater;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
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
            convertView = inflater.inflate(R.layout.grid_item,null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgP1);
        TextView campo1 = convertView.findViewById(R.id.tv_campo1);
//        TextView campo2 = convertView.findViewById(R.id.tv_campo2);
        TextView campo3 = convertView.findViewById(R.id.tv_campo3);
        ImageView imageFavorite = convertView.findViewById(R.id.img_favorite);

        TextView tvId = convertView.findViewById(R.id.tv_id);

        Button detalles = convertView.findViewById(R.id.btn_prod_detail);

        Producto producto = productos.get(position);
        byte[] image = producto.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        tvId.setText("ID: " + producto.getId());
        campo1.setText(producto.getName());
//        campo2.setText(producto.getDescription());
        campo3.setText(producto.getPrice());

        if(producto.getFavorite().equals("1")){
            imageFavorite.setColorFilter(ContextCompat.getColor(context, R.color.third));
        }else if(producto.getFavorite().equals("0")){
            imageFavorite.setColorFilter(Color.argb(128, 128, 128, 128));
        }
        imageView.setImageBitmap(bitmap);

        detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("name", producto.getName());
                    i.putExtra("description", producto.getDescription());
                    i.putExtra("img", producto.getImage());
                    i.putExtra("price", producto.getPrice());
                    i.putExtra("favorite", producto.getFavorite());
                    i.putExtra("identificador", String.valueOf(producto.getId()));
                    context.startActivity(i);
                } catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("Error ->", e.toString());

                }


            }
        });

        return convertView;
    }
}
