package com.example.mana.nexo_app.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by MANA on 2/11/2018.
 */

public class ApplicationContext extends Application {
    private static ApplicationContext context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
    public static ApplicationContext getContext()
    {
        return context;
    }
    public static Context getAppContext()
    {
        return context.getApplicationContext();
    }


}
