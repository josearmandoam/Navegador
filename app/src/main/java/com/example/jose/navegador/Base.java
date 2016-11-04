package com.example.jose.navegador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 18/10/2016.
 */

public class Base extends SQLiteOpenHelper{
    static Historial historial;
    static String ins="CREATE TABLE Historial (texto VARCHAR(20),url VARCHAR(100),hora VARCHAR(10))";
    static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="Historial.db";
    public Base(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        *
        *
        *
        */
    }

    public int añadirPagina(Historial historial){
        int r_modificados=-1;
        String text=historial.getTexto();
        String url=historial.getUrl();
        String hora=historial.getHora();

        SQLiteDatabase db=this.getWritableDatabase();

        if(db!=null){
            ContentValues valores=new ContentValues();

            valores.put("texto",text);
            valores.put("url",url);
            valores.put("hora",hora);

            r_modificados=(int)db.insert("Historial",null,valores);

            db.close();
        }
        return r_modificados;
    }
    public List<Historial> obtenerHistorial(){
        Historial historial;
        List<Historial> lista_historial=new ArrayList<Historial>();

        SQLiteDatabase db=this.getReadableDatabase();
        String campos[]={"texto","url","hora"};
        Cursor c;

        if(db!=null){
            c=db.query("Historial",campos,null,null,null,null,null);
            c.moveToFirst();
            do{
                historial=new Historial(c.getString(0),c.getString(1),c.getString(2));
                lista_historial.add(historial);
            }while(c.moveToNext());
            db.close();
            c.close();
        }

        return lista_historial;
    }
    public int borrarHistorial(){

        int n=-1;
        SQLiteDatabase db=this.getWritableDatabase();
        if(db!=null){
            n=db.delete("Historial",null,null);
        }
        db.close();
        return n;
    }

}
