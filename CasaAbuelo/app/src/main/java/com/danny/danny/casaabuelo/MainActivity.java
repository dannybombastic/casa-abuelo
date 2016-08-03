package com.danny.danny.casaabuelo;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.danny.danny.casaabuelo.MiServicio_pull_IT.Mibroadcastreciver;
import com.danny.danny.casaabuelo.MiServicio_pull_IT.Notificacion;
import com.danny.danny.casaabuelo.servicios_GMC.RegistrarIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.InterruptedIOException;
import java.util.zip.Inflater;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,CartasFragment.OnFragmentInteractionListener {
    private final String API_KEY = "AIzaSyDAa-cINqlkS136GyP20Nc1Kbgf69sWyxM";
    private final String SENDER_ID = "353909233919";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    BroadcastReceiver MensajepushRecciver;
    Mibroadcastreciver MiReceptor2;
    IntentFilter intentFilter,intentfilter2;
    //  MibroadcastreciverMain MiReceptor;
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+34952663866"));
                startActivity(intent);
                Snackbar.make(view, "LLAMANDO...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        MensajepushRecciver  = new BroadcastReceiver() {

            //   metodo onRecive de la clase BroadCastReciver  el cual recive el evento de una acion
            @Override
            public void onReceive(Context context, Intent intent) {
                // si el intent acaba en el valor de la costante REGISTRO_OK de la clase registraid
                if(intent.getAction().endsWith(RegistrarIntentService.REGISTRO_OK)){
                    //recupera cla clave /valor token
                    String token = intent.getStringExtra("token");
                    Toast.makeText(getApplicationContext(),"token enviado",Toast.LENGTH_SHORT).show();
                    //si por contra el intent acacba con la costante error  informa del fallo en el registro
                }else if(intent.getAction().endsWith(RegistrarIntentService.REGISTRO_ERR)){


                    Toast.makeText(getApplicationContext(),"token error",Toast.LENGTH_SHORT).show();
                    // verifica que el teminal tenga instalado el PlayStore o al menos lo servicios de google
                }else if(!checkPlayServices()){

                    Intent itent = new Intent(getApplicationContext(),RegistrarIntentService.class);
                    startService(itent);
                }


            }
        };

        if (verificaConexion(getApplicationContext())) {
            // Notificacion.class
            servicio();





        } else {

           Crouton.makeText(this, "ERROR CONEX.", Style.INFO).show();
        }

        // broadcast de manera declarativa , esta declarado en el manifest con un intenfilter con una etiqueta de accion
        MiReceptor2 = new Mibroadcastreciver();
        // instanciamos el intentfilter o accion y lo registramos en el main o setContenView
        //con esta accion recupero con una peticion web en formato Json los valores de toda la aplicacion en una sola vez
        // esta temporizada para hacer una peticion pull-it desde mi base de datos esta lanzada con un senBroadCast desde un servicio Notificacion.class
        intentFilter = new IntentFilter("ACTUALIZAR_DOS");
        registerReceiver(MiReceptor2, intentFilter);
        MiReceptor2.isInitialStickyBroadcast();


        // iniciamos el servicio de registro con google
        if( checkPlayServices() ){
            Intent it = new Intent(this, RegistrarIntentService.class);
            startService(it);
        }

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.llamar) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+34952663866"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new MenusFragment();
        } else if (id == R.id.nav_gallery) {
            fragment = new CartasFragment();
        } else if (id == R.id.nav_slideshow) {
            fragment = new FutbolFragment();
        } else if (id == R.id.nav_manage) {
            fragment = new EventosFragment();
        } else if (id == R.id.nav_share) {
            fragment = new UbicacionFragment();
        } else if (id == R.id.nav_send) {
            fragment = new DibujarFragment();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.content_main,fragment)
                .commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // metodo que lanza mi servicio
    public void servicio(){

        //  Notificacion nr = new Notificacion();


        startService(new Intent(getApplicationContext(),Notificacion.class));


    }

    // verificamos el estado de internet 3g y wifi
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }


    @Override
    protected void onResume() {
        super.onResume();
        servicio();
        // registrando un broadcast programatico
        LocalBroadcastManager.getInstance(this).registerReceiver(MensajepushRecciver, new IntentFilter(RegistrarIntentService.REGISTRO_OK) );
        LocalBroadcastManager.getInstance(this).registerReceiver(MensajepushRecciver, new IntentFilter(RegistrarIntentService.REGISTRO_ERR) );
        // registrando un broadcast declarativo
        registerReceiver(MiReceptor2,intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //   programatico
        LocalBroadcastManager.getInstance(this).unregisterReceiver(MensajepushRecciver);
        // declarativo
        unregisterReceiver(MiReceptor2);

    }

    // metodo para verificar   si tenemos el play store y servicios google
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("LOG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
