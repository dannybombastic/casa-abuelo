package com.danny.danny.casaabuelo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private final static long SPLASHSCREEM_DELAY = 3000;
    Button flecha;
    SplashThread teatre;
    boolean actividad = false;
    Timer temporizador = new Timer();

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = getSharedPreferences("menus", Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();

        flecha = (Button)findViewById(R.id.button_flecha) ;
        teatre = new SplashThread(this);
        if(pref.getBoolean("splash",false)==false) {
            temporizador.schedule(teatre, SPLASHSCREEM_DELAY);
        }else{
            temporizador.cancel();
            Intent intenta = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intenta);
            finish();
        }
            editor.putBoolean("splash",true);
            editor.commit();



        flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temporizador.cancel();
                Intent intenta = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intenta);
                finish();
            }
        });


    }






    @Override
    protected void onResume() {
        super.onResume();

    }
    public class SplashThread extends TimerTask {
        Context context;

        int layout = 0;
        public SplashThread(Context ctx){
            this.context = ctx;

        }
        @Override
        public void run() {


            //  layout = R.layout.activity_main;
            Intent intenta = new Intent().setClass(this.context, MainActivity.class);
            context.startActivity(intenta);
            finish();



        }





    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         super.onKeyDown(keyCode,event);
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){

            temporizador.cancel();
            Intent intenta = new Intent().setClass(getApplicationContext(), MainActivity.class);
            startActivity(intenta);
            finish();
            Log.d("temporizador","pulsando atras");
        }

        return true;
    }
}
