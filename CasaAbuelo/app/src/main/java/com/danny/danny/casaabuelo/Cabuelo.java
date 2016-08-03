package com.danny.danny.casaabuelo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.danny.danny.casaabuelo.MiServicio_pull_IT.Mibroadcastreciver;
import com.danny.danny.casaabuelo.MiServicio_pull_IT.Notificacion;
import com.danny.danny.casaabuelo.servicios_GMC.RegistrarIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;


import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Cabuelo extends Activity {
    private final String API_KEY = "AIzaSyDAa-cINqlkS136GyP20Nc1Kbgf69sWyxM";
    private final String SENDER_ID = "353909233919";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    Notification notif;
    NotificationManager nm;
    Button dibujar;

    TextView menu, segundos, terceros, eventos, partidos;
    ImageButton boton, llamada, cartas, balon;
    ProgressBar interior;
    ProgressBar terraza;
    SharedPreferences pref;
    private JSONArray products2 = null;
    private JSONArray products1 = null;
    Intent datosBr;
    int p_interior = 0;
    int icon = R.drawable.logook;
    BroadcastReceiver MensajepushRecciver;
    Mibroadcastreciver MiReceptor2;
    IntentFilter intentFilter,intentfilter2;
    //  MibroadcastreciverMain MiReceptor;
    Bitmap bit;


    String url_menus = "http://dannybombastic.esy.es/abueloall.php";
    String url_elecc = "http://dannybombastic.esy.es/elecc.php";
    String url_elec = "http://dannybombastic.esy.es/noti2.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           // hace que la barra de notificaciones desaparezca y pase a fullscreen
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

          // metodo de la activity al que se le poasa una vista en este caso es el layout.xml de la activity
        setContentView(R.layout.activity_cabuelo);

       // Referencias a los componentes del XML
        dibujar = (Button)findViewById(R.id.buttonDibujar) ;
        eventos = (TextView) findViewById(R.id.evento);
        partidos = (TextView) findViewById(R.id.partido);
        balon = (ImageButton) findViewById(R.id.balon);
        segundos = (TextView) findViewById(R.id.segundos);
        terceros = (TextView) findViewById(R.id.terceros);
        llamada = (ImageButton) findViewById(R.id.llamada2);
        menu = (TextView) findViewById(R.id.primeros);
        boton = (ImageButton) findViewById(R.id.Button);
        interior = (ProgressBar) findViewById(R.id.interior);
        terraza = (ProgressBar) findViewById(R.id.terraza);
        cartas = (ImageButton) findViewById(R.id.imageButton2);
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.notif);
        String ns = Context.NOTIFICATION_SERVICE;
        nm = (NotificationManager) getSystemService(ns);
          // declaracion de un broadcast reciber de forma programatica implicita sin añadir nada en el manifest
                 MensajepushRecciver  = new BroadcastReceiver() {

           //   metodo onRecive de la clase BroadCastReciver  el cual recive el evento de una acion
           @Override
           public void onReceive(Context context, Intent intent) {
               // si el intent acaba en el valor de la costante REGISTRO_OK de la clase registraid
         if(intent.getAction().endsWith(RegistrarIntentService.REGISTRO_OK)){
             //recupera cla clave /valor token
             String token = intent.getStringExtra("token");
             Toast.makeText(getApplicationContext(),"token"+token,Toast.LENGTH_SHORT).show();
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



      //  como temporizar una solicitur de arranque de un servicio un una accion en un intent
      /* Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, Notificacion.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
       Iniciar cada 30 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pintent);
      */

             // verificacion del estado del la conexion con inter
        if (verificaConexion(Cabuelo.this)) {
             // Notificacion.class
           servicio();





        } else {

            Crouton.makeText(Cabuelo.this, "ERROR CONEX.", Style.INFO).show();
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


        eventos.setClickable(true);
        partidos.setClickable(true);


        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+34952663866"));
                startActivity(intent);

            }
        });






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



}
