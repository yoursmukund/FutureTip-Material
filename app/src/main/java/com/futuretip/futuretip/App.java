package com.futuretip.futuretip;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanova.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
