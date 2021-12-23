package com.cengtel.reto2.ui.sucursales;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;


import com.cengtel.reto2.MapsActivity;
import com.cengtel.reto2.R;

import com.cengtel.reto2.adaptadores.SucursalAdapter;

import com.cengtel.reto2.casos_uso.CasoUsoSucursal;

import com.cengtel.reto2.datos.DBHelper;

import com.cengtel.reto2.modelos.Sucursal;

import java.util.ArrayList;


public class SucursalesFragment extends Fragment {
    private String TABLE_NAME = "SUCURSALES";
    private CasoUsoSucursal casoUsoSucursal;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Sucursal> sucursales;



    public View onCreateView(@NonNull  LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sucursales, container, false);

        try {
            casoUsoSucursal = new CasoUsoSucursal();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            sucursales = casoUsoSucursal.llenarListaSucursales(cursor);
            gridView = (GridView) root.findViewById(R.id.gridSucursales);
            SucursalAdapter sucursalAdapter = new SucursalAdapter(root.getContext(), sucursales);
            gridView.setAdapter(sucursalAdapter);

        } catch (Exception e){
            Toast.makeText(getContext(), "SUCURSALES " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putExtra("name","SUCURSALES") ;
                getActivity().startActivity(i);
                Toast.makeText(getContext(), "Sucursales", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}