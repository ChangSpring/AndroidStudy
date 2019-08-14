package com.alfred.androidstudy.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author :  Alfred
 * @date : 2019-08-13 14:50
 */
public class AppBean implements Serializable {
    private String packageName;
    private String appName;
    private String versionName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @NonNull
    @Override
    public String toString() {
        return "packageName = " + packageName + " appName = " + appName + " versionName = " + versionName;
    }
}
