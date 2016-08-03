package com.danny.danny.casaabuelo.MiServicio_pull_IT;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.danny.danny.casaabuelo.MiServicio_pull_IT.Notificacion;

public class BootupReceiver extends BroadcastReceiver {
    public BootupReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"app iniciada",Toast.LENGTH_LONG).show();
        Intent i = new Intent(context,Notificacion.class);
        // añadimos una marca de nueva activity que se lanzara al arranuqe
        // podiamos decir que se mete en el cajon de nuevas activitys a lanzar desde XML
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i);

    }
}
