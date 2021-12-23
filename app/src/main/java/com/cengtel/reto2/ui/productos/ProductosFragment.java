package com.cengtel.reto2.ui.productos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.cengtel.reto2.AgregadosActivity;
import com.cengtel.reto2.R;
import com.cengtel.reto2.adaptadores.ProductoAdapter;
import com.cengtel.reto2.casos_uso.CasoUsoProducto;
import com.cengtel.reto2.databinding.FragmentProductosBinding;
import com.cengtel.reto2.datos.DBHelper;
import com.cengtel.reto2.modelos.Producto;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductosFragment extends Fragment {
    private String TABLE_NAME = "PRODUCTOS";
    private CasoUsoProducto casoUsoProducto;
    private GridView gridView;
    private DBHelper dbHelper;
    private ArrayList<Producto> productos;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_productos, container, false);
        try {
            casoUsoProducto = new CasoUsoProducto();
            dbHelper = new DBHelper(getContext());
            Cursor cursor = dbHelper.getData(TABLE_NAME);
            productos = casoUsoProducto.llenarListaProductos(cursor);
            gridView = (GridView) root.findViewById(R.id.gridProductos);
            ProductoAdapter productoAdapter = new ProductoAdapter(root.getContext(), productos);
            gridView.setAdapter(productoAdapter);

        } catch (Exception e){
            Toast.makeText(getContext(), "PRODUCTOS " + e.toString(), Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(getContext(), AgregadosActivity.class);
                i.putExtra("name","PRODUCTOS") ;
                getActivity().startActivity(i);
                Toast.makeText(getContext(), "Productos", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}