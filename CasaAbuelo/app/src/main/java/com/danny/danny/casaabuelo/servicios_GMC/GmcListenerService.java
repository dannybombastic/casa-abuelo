package com.danny.danny.casaabuelo.servicios_GMC;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.danny.danny.casaabuelo.*;
import com.danny.danny.casaabuelo.R;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GmcListenerService extends GcmListenerService {

    int icon = com.danny.danny.casaabuelo.R.drawable.logook;

    Bitmap bit;

    public GmcListenerService() {
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String mensage = data.getString("data");
        String titulo ="";
        String estado ="";
        try {
            JSONObject jsonResp = new JSONObject(new String(data.getString("data")));
            titulo = jsonResp.getString("titulo");
            mensage = jsonResp.getString("data");
            estado = jsonResp.getString("estado");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        notificar(icon,estado,titulo,mensage,new Intent(getApplicationContext(),MainActivity.class));
    }

    @Override
    public void onSendError(String msgId, String error) {
        super.onSendError(msgId, error);
    }
    static int i =0;
    public void notificar(int icon,CharSequence textoEstado,CharSequence titulo,CharSequence textoContenido,Intent even) {
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


}
