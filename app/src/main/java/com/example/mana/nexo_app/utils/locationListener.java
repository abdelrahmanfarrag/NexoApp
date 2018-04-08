package com.example.mana.nexo_app.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANA on 2/27/2018.
 */

public class locationListener implements android.location.LocationListener{
    Context ctx;
    public locationListener(Context ctx)
    {
        this.ctx=ctx;
    }


    @Override
    public void onLocationChanged(Location location) {


            try {
              /*  double loc_long= location.getLongitude();
                double loc_lat = location.getLatitude();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("TEMP");
                Map M = new HashMap<>();
                M.put("LATITUDE",loc_lat);
                M.put("LONGITUTDE",loc_long);
                ref.setValue(M);*/
                Toast.makeText(ctx,String.valueOf(location),Toast.LENGTH_LONG).show();

            }

        catch (Exception e)
        {
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {


    }

    @Override
    public void onProviderEnabled(String s) {
Toast.makeText(ctx,"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {

        Toast.makeText(ctx,"Check Wifi or Location information Status !",Toast.LENGTH_LONG).show();


    }
}
