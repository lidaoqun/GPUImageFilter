package com.lidaoqun.gpuimagefilter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/8/30.
 */

public class FilterApplication extends Application {
    public static Context sContext ;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
