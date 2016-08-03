package com.danny.danny.casaabuelo.MiServicio_pull_IT;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Mibroadcastreciver extends BroadcastReceiver {
String exterior;
    String interior;
    String primero;
    String segundo;
    String tercero;
    String equipoa;
    String quipob;
    String hora;
    String evento;
    String nombre;
    String equipob;
    Intent puente;


    SharedPreferences pref;
    public Mibroadcastreciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences("menus", Context.MODE_APPEND);

        exterior = intent.getStringExtra("exterior");
        interior = intent.getStringExtra("interior");
        primero = intent.getStringExtra("primero");
        segundo = intent.getStringExtra("segundo");
        tercero = intent.getStringExtra("tercero");
        equipoa = intent.getStringExtra("equipoa");
        equipob = intent.getStringExtra("equipob");
        hora = intent.getStringExtra("hora");
        evento = intent.getStringExtra("evento");
        nombre = intent.getStringExtra("nombre");
        Log.d("BR", "no para");


        setPreferencias(intent);
     //  Toast.makeText(context, "broad cast evento"+"no para", Toast.LENGTH_SHORT).show();







    }




    public void setPreferencias(Intent i){

        if(i!=null) {



            SharedPreferences.Editor editor = pref.edit();
            editor.putString("mesas", i.getStringExtra("exterior"));
            editor.putString("terraza", i.getStringExtra("interior"));
            editor.putString("primero", i.getStringExtra("primero"));
            editor.putString("segundo", i.getStringExtra("segundo"));
            editor.putString("terceros", i.getStringExtra("tercero"));
            editor.putString("equipoa", i.getStringExtra("equipoa"));
            editor.putString("equipob", i.getStringExtra("equipob"));
            editor.putString("hora", i.getStringExtra("hora"));
            editor.putString("anuncio", i.getStringExtra("evento"));
            editor.putString("evento", i.getStringExtra("nombre"));
            editor.putString("fecha", i.getStringExtra("fecha"));
            editor.apply();
            editor.commit();
        }
    }


}