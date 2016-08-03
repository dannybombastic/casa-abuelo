package com.danny.danny.casaabuelo.MiServicio_pull_IT;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.danny.danny.casaabuelo.MainActivity;
import com.danny.danny.casaabuelo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Notificacion extends Service {


    private final Timer timer = new Timer();
     Context context;

    private  String mesas,terraza,equipoa,equipob,hora,evento,nombre,primero,segundo,tercero,eleccion,fecha;
    private int alarma;
    private  int not;
    private int not2;



    protected String [] nuevo;
    protected boolean banderaN;
    protected boolean bandera =false;
    protected boolean banderaNotif = false;
    final String OB_NUMERO = "http://dannybombastic.esy.es/actualizando/obNumero.php";
    String OB_PIZARRA = "http://dannybombastic.esy.es/actualizando/obPizarra.php";


    int icon = R.drawable.logook;

    Bitmap bit;

  public Notificacion(){
      super();

  }






    public Notificacion(Context ctx){
        super();
        this.context=ctx;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new BuscarTask().execute();
       // timer.schedule(new terceraTarea(),1000);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

             //   timer.schedule(new segundaTarea(), 1000);
            //    timer.schedule(new primeraTarea(), 1000);
                 new BuscarTask().execute();
        //timer.schedule(new primeraTarea(), 1000,15000);
       // timer.schedule(new (),3000,15000);

    }
    public void datos(String[] datos){

        if(datos!=null) {
            Intent o = new Intent("ACTUALIZAR_DOS");
         //   Intent i = new Intent("ACTUALIZAR_TRES");

            o.putExtra("exterior", datos[0]);
            o.putExtra("interior", datos[1]);
            o.putExtra("primero", datos[2]);
            o.putExtra("segundo", datos[3]);
            o.putExtra("tercero", datos[4]);
            o.putExtra("equipoa", datos[5]);
            o.putExtra("equipob", datos[6]);
            o.putExtra("hora", datos[7]);
            o.putExtra("evento", datos[8]);
            o.putExtra("nombre", datos[9]);
            o.putExtra("fecha",datos[10]);
         //   sendBroadcast(i);
              sendBroadcast(o);
           // setPreferencias(o);
        }

    }

    public void setPreferencias(Intent i){

        if(i!=null) {
           SharedPreferences pref = getSharedPreferences("menus", Context.MODE_APPEND);
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
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

 static int i=1;

    public void notificar(int icon,CharSequence textoEstado,CharSequence titulo,CharSequence textoContenido,String numero,Intent even) {
        //  even = new Intent(even.ACTION_VIEW, even));

        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, even, 0);
        bit = BitmapFactory.decodeResource(getResources(), R.drawable.notif);
        //Construccion de la notificacion;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setSmallIcon(icon);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setLargeIcon(redimensionarImagenMaximo(bit, 120, 120));
        builder.setContentTitle(titulo);
        builder.setContentText(textoContenido);
        builder.setSubText(textoEstado);

        //Enviar la notificacion
        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(i++,builder.build());

    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }





    public class BuscarTask extends AsyncTask<Void,Void,Void> {
        final Timer timer = new Timer();
        int noti;
        OkHttpClient client;

        @Override
        protected Void doInBackground(Void... params) {
            // timer.schedule(new Condicional(),1000,2000);


            //  timer.schedule(new segundaTarea(), 20000,20000);
            timer.schedule(new primeraTarea(), 30000, 25000);
               return null;
        }
    }

    class segundaTarea extends TimerTask{




        @Override
        public void run() {

            URL url = null;
            String devuelve = "";

            try {
                url = new URL(OB_PIZARRA);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

                    conexion.setRequestProperty("User-Agent", "Mozilla/5.0" + "(Linux; Android 1.5; es-ES) Ejemplo HTTP");
                if(Build.VERSION.SDK_INT>13){
                    conexion.setRequestProperty("Connection","close");

                }
                int respuesta = conexion.getResponseCode();
                StringBuilder result = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {

                    InputStream in = new BufferedInputStream(conexion.getInputStream());
                    BufferedReader lector = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = lector.readLine()) != null) {
                        result.append(line);        // Paso toda la entrada al StringBuilder
                    }
                    JSONObject respuestaJson = new JSONObject(result.toString());
                    String resultJson = respuestaJson.getString("estado");
                    if (resultJson == "1") {
                        JSONArray pizarra = respuestaJson.getJSONArray("alumnos");

                        for (int i = 0; i < pizarra.length(); i++) {
                            mesas = pizarra.getJSONObject(i).getString("mesas");
                            terraza = pizarra.getJSONObject(i).getString("terraza");
                            primero = pizarra.getJSONObject(i).getString("primero");
                            segundo = pizarra.getJSONObject(i).getString("segundo");
                            tercero = pizarra.getJSONObject(i).getString("tercero");
                            equipoa = pizarra.getJSONObject(i).getString("equipoa");
                            equipob = pizarra.getJSONObject(i).getString("equipob");
                            fecha = pizarra.getJSONObject(i).getString("fecha");
                            hora = pizarra.getJSONObject(i).getString("horafut");
                            evento = pizarra.getJSONObject(i).getString("anuncio");
                            nombre = pizarra.getJSONObject(i).getString("evento");
                            eleccion = pizarra.getJSONObject(i).getString("notificacion");
                            not2 = Integer.parseInt(pizarra.getJSONObject(i).getString("not"));
                            devuelve = "1";
                            Log.d("datos", "mi segunda tarea" + " datos :" + terraza +" nomb : "+ nombre);

                             }

                       if(terraza!=null){
                            nuevo = new String[]{mesas,terraza,primero,segundo,tercero,equipoa,equipob,hora,evento,nombre,fecha};
                           datos(nuevo);

                       }

                    } else if (resultJson == "2") {
                        devuelve = "2";


                    } else {
                        devuelve = "2";
                    }


                    }

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



            Log.d("segunda", "mi segunda tarea :" +alarma+ " notificacion :" + not+" devuelve"+ devuelve);


        }


    }

       class primeraTarea extends TimerTask {



           OkHttpClient client;
           @Override
           public void run() {




               try {
                   client = new OkHttpClient();
                   JSONObject respuestaJson = null;
                   respuestaJson = new JSONObject(run(OB_PIZARRA));



                  JSONArray pizarra = null;

                   pizarra = respuestaJson.getJSONArray("alumnos");
                   for (int i = 0; i < pizarra.length(); i++) {
                       mesas = pizarra.getJSONObject(i).getString("mesas");
                       terraza = pizarra.getJSONObject(i).getString("terraza");
                       primero = pizarra.getJSONObject(i).getString("primero");
                       segundo = pizarra.getJSONObject(i).getString("segundo");
                       tercero = pizarra.getJSONObject(i).getString("tercero");
                       equipoa = pizarra.getJSONObject(i).getString("equipoa");
                       equipob = pizarra.getJSONObject(i).getString("equipob");
                       fecha = pizarra.getJSONObject(i).getString("fecha");
                       hora = pizarra.getJSONObject(i).getString("horafut");
                       evento = pizarra.getJSONObject(i).getString("anuncio");
                       nombre = pizarra.getJSONObject(i).getString("evento");
                       eleccion = pizarra.getJSONObject(i).getString("notificacion");
                       not2 = Integer.parseInt(pizarra.getJSONObject(i).getString("not"));

                       Log.d("datos", "mi primera" + " datos :" + terraza + " nomb : " + nombre);

                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }catch (IOException e) {
                   e.printStackTrace();
               }



               if (terraza != null) {
                   nuevo = new String[]{mesas, terraza, primero, segundo, tercero, equipoa, equipob, hora, evento, nombre, fecha};
                   datos(nuevo);

               }


           }
           public  boolean verificaConexion(Context ctx) {
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

           public String run(String url) throws IOException {
               if(verificaConexion(getApplicationContext())){
                   Request request = new Request.Builder()
                           .url(url)
                           .build();

                   Response response = client.newCall(request).execute();
                   return response.body().string();
               }else{
                   return "no hay respuesta";
               }

           }
       }

    class terceraTarea extends TimerTask{




        @Override
        public void run() {

                URL url = null;
                String devuelve = "";
                try {
                    url = new URL(OB_NUMERO);
                    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                    conexion.setRequestProperty("User-Agent", "Mozilla/5.0" + "(Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = conexion.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(conexion.getInputStream());
                        BufferedReader lector = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = lector.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }
                        JSONObject respuestaJson = new JSONObject(result.toString());
                        String resultJson = respuestaJson.getString("estado");

                        if (resultJson == "1") {

                                not = Integer.parseInt(respuestaJson.getJSONObject("alumnos").getString("not"));
                                eleccion = respuestaJson.getJSONObject("alumnos").getString("notificacion");
                            Log.d("bucle", " bucle numero :"+not+ " noti :" + not2 );
                            devuelve = "1";




                        } else if (resultJson == "2") {
                            devuelve = "2";


                        } else {
                            devuelve = "2";
                        }

                    }

                } catch (MalformedURLException e) {

                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
/**
                if (not2 != not ) {
                   not2=not;
                    banderaN=true;
                }else{
                    banderaN=false;
                }
              if(banderaN) {
                  banderaN=false;
                  not2=not;
                  switch (eleccion) {
                      case "1":
                          notificar(icon, equipoa + " Vs " + equipob, "FUTBOL " + "A LAS :" + hora, " 952-663-866 telf.", "FUTBOL CASA EL ABUELO", new Intent(getApplicationContext(), Partidos.class));
                          banderaN=false;
                          break;
                      case "2":
                          notificar(icon, "952-663-866 telf.", nombre, evento, "CASA EL ABUELO", new Intent(getApplicationContext(), Eventos.class));
                          banderaN=false;
                          break;
                      case "3":
                          notificar(icon, "952-663-866 telf.", "MENU DEL DIA", primero + segundo + tercero, "CASA EL ABUELO MENU DEL DIA", new Intent(getApplicationContext(), Cabuelo_menu.class));
                          banderaN=false;
                          break;


                  }

              }else{
                  banderaN=false;
              }

*/




        }
    }

    public class Condicional extends TimerTask{


        @Override
        public void run() {
            if (not2 != not ) {
                   not=not2;
                banderaN=true;
            }else{
                banderaN=false;
            }
            if(banderaN) {
                banderaN=false;
                 not=not2;
                switch (eleccion) {
                    case "1":
                        notificar(icon, equipoa + " Vs " + equipob, "FUTBOL " + "A LAS :" + hora, " 952-663-866 telf.", "FUTBOL CASA EL ABUELO", new Intent(getApplicationContext(),  MainActivity.class));
                        banderaN=false;
                        break;
                    case "2":
                        notificar(icon, "952-663-866 telf.", nombre, evento, "CASA EL ABUELO", new Intent(getApplicationContext(), MainActivity.class));
                        banderaN=false;
                        break;
                    case "3":
                        notificar(icon, "952-663-866 telf.", "MENU DEL DIA", primero + segundo + tercero, "CASA EL ABUELO MENU DEL DIA", new Intent(getApplicationContext(),  MainActivity.class));
                        banderaN=false;
                        break;


                }

            }else{
                banderaN=false;
            }

        }
    }

}
