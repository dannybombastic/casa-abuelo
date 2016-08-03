package com.danny.danny.casaabuelo.servicios_GMC;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MiIstanciaID extends InstanceIDListenerService {


    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this,RegistrarIntentService.class);
        startService(intent);
    }
}