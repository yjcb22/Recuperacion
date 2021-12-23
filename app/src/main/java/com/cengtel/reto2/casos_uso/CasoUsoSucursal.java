package com.cengtel.reto2.casos_uso;

import android.database.Cursor;

import com.cengtel.reto2.modelos.Producto;
import com.cengtel.reto2.modelos.Sucursal;

import java.util.ArrayList;

public class CasoUsoSucursal {

    public ArrayList<Sucursal> llenarListaSucursales(Cursor cursor){
        ArrayList<Sucursal> list = new ArrayList<>();
        if(cursor.getCount() ==0) {
            return  list;
        } else {
            while (cursor.moveToNext()){
                Sucursal sucursal = new Sucursal(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getBlob(5)
                );

                list.add(sucursal);
            }
            return list;

        }
    }
}
