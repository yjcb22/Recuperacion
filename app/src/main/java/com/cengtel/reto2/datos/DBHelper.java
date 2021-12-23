package com.cengtel.reto2.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;


import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(@Nullable Context context) {

        super(context, "Reto3.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE PRODUCTOS("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR," +
                "description VARCHAR,"+
                "price VARCHAR,"+
                "favorite VARCHAR,"+
                "image BLOB"+
                ")");

        db.execSQL("CREATE TABLE SERVICIOS("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR," +
                "description VARCHAR,"+
                "price VARCHAR,"+
                "favorite VARCHAR,"+
                "image BLOB"+
                ")");

        db.execSQL("CREATE TABLE SUCURSALES("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR," +
                "description VARCHAR,"+
                "location VARCHAR,"+
                "favorite VARCHAR,"+
                "image BLOB"+
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS servicios");
        db.execSQL("DROP TABLE IF EXISTS sucursales");

    }

    //METODOS PROPIOS

    public void insertData(String campo1, String campo2, String campo3, String campo4, byte[] image, String table){
        String sql = "INSERT INTO " + table +" VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, campo1);
        statement.bindString(2, campo2);
        statement.bindString(3, campo3);
        statement.bindString(4, campo4);
        statement.bindBlob(5, image);

        statement.executeInsert();
    }

    public Cursor getData(String table){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+table,null);
        return cursor;
    }

    public Cursor getDataById(String table, String Id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+table+" WHERE id = "+Id,null);
        return cursor;
    }
    public void deleteDataById(String table, String Id){
        sqLiteDatabase.execSQL("DELETE FROM "+table+" WHERE id= " + Id);
    }

    public void updateProductoById(String table, String id, String campo1, String campo2, String campo3, String campo4, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", campo1);
        contentValues.put("description", campo2);
        contentValues.put("price", campo3);
        contentValues.put("favorite", campo4);
        contentValues.put("image", image);
        sqLiteDatabase.update(table, contentValues, "id = ?", new String[]{id});
    }

    public void updateServicioById(String table, String id, String campo1, String campo2, String campo3, String campo4, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", campo1);
        contentValues.put("description", campo2);
        contentValues.put("price", campo3);
        contentValues.put("favorite", campo4);
        contentValues.put("image", image);
        sqLiteDatabase.update(table, contentValues, "id = ?", new String[]{id});
    }

    public void updateSucursalById(String table, String id, String campo1, String campo2, String campo3, String campo4, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", campo1);
        contentValues.put("description", campo2);
        contentValues.put("location", campo3);
        contentValues.put("favorite", campo4);
        contentValues.put("image", image);
        sqLiteDatabase.update(table, contentValues, "id = ?", new String[]{id});
    }

}
