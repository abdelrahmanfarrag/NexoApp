package com.example.mana.nexo_app.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;

/**
 * Created by MANA on 2/22/2018.
 */

public class SinchService {
    Context ctx;
    String id;
    private static final String APPLICATION_KEY="6107d3bd-f1df-4554-8c46-02ce075b37bc";
    private static final String APPLICATION_SECRET ="DRh+O6FNR0OvC3fN/pV/hw==";
    private static final String HOST_NAME = "clientapi.sinch.com";
    public SinchService(Context ctx,String id)
    {
        this.ctx=ctx;
        this.id=id;
    }
  String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    android.content.Context context = this.ctx;
   final SinchClient sinchClient = Sinch.getSinchClientBuilder().context(context)
            .applicationKey(APPLICATION_KEY)
            .applicationSecret(APPLICATION_SECRET)
            .environmentHost(HOST_NAME)
            .userId(id)
            .build();






}
