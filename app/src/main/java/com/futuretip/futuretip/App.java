package com.futuretip.futuretip;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
