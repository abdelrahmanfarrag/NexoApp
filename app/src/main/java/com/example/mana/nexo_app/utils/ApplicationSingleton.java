package com.example.mana.nexo_app.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by MANA on 2/11/2018.
 */

public class ApplicationSingleton {
    private static ApplicationSingleton Sinstance=null;
    private RequestQueue Squeue;

    private ApplicationSingleton()
    {
        Squeue= Volley.newRequestQueue(ApplicationContext.getAppContext());
    }
    public static ApplicationSingleton getInstance()
    {
        if (Sinstance==null)
        {
            Sinstance=new ApplicationSingleton();
        }
        return Sinstance;
    }
    public RequestQueue getQueue()
    {
        return Squeue;
    }




}
