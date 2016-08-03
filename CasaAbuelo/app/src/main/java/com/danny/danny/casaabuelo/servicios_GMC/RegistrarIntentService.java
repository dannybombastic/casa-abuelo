package com.danny.danny.casaabuelo.servicios_GMC;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RegistrarIntentService extends IntentService {
    public static final String LOG = "LOG";
    public static final String REGISTRO_OK = "Registrado";
    public static final String REGISTRO_ERR = "Registro Error";


    public RegistrarIntentService(){
        super(LOG);
    }

    @Override
 protected void onHandleIntent( Intent intent) {
        synchronized (LOG) {
            registrar();

        }
    }



   private void registrar(){
       Intent registroCompletado = null;
       String token = null;
       InstanceID instanceID = InstanceID.getInstance( this );
       SharedPreferences guardar = getSharedPreferences("menus", Context.MODE_APPEND);
       boolean registrado = guardar.getBoolean(QuickStartPreference.SENT_TOKEN_TO_SERVER,false);
       if(!registrado) {
       try{

               token = instanceID.getToken("353909233919",
                       GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);

               Log.v("registro", "token" + token);
               registroCompletado = new Intent(REGISTRO_OK);
               registroCompletado.putExtra("token", token);
               enviarClave(token);

               guardar.edit().putBoolean(QuickStartPreference.SENT_TOKEN_TO_SERVER, true).apply();

       }catch (Exception e){
           Log.v("registro","Registro error");
           guardar.edit().putBoolean(QuickStartPreference.SENT_TOKEN_TO_SERVER, false).apply();
           registroCompletado = new Intent(REGISTRO_ERR);
       }
       LocalBroadcastManager.getInstance(this).sendBroadcast(registroCompletado);
       }
   }

    public void enviarClave(String clave){

        try {
            HttpURLConnection urlConn;
            DataOutputStream printOut;
            DataInputStream input;
            URL url = new URL("http://dannybombastic.esy.es/actualizando/clav.php?");
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);

            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type","applicacion/json");
            urlConn.setRequestProperty("Accept","applicacion/json");
            urlConn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("clave",clave);


            //   JSONArray array = new JSONArray();




            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            Log.v("json",jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();


            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK) {

                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                    //response+=line;
                }





            }




        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
