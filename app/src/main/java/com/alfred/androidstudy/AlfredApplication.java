package com.alfred.androidstudy;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by alfred on 2017/12/11.
 */

public class AlfredApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
