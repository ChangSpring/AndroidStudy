package com.alfred.androidstudy;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by alfred on 2017/12/11.
 */

public class AlfredApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        FlowManager.init(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
