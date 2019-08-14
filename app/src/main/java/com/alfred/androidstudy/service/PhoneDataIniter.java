package com.alfred.androidstudy.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * @author :  Alfred
 * @date : 2019-08-12 10:56
 */
public class PhoneDataIniter {

    public void init(Context context) {
        //设置定时操作
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //1分钟请求一次更新
        int elapseTime = 1 * 60 * 1000;
        long interval = SystemClock.elapsedRealtime() + elapseTime;
        //传递userId参数给PhoneDataTaskReceiver,为了到时候回传回来
        Intent i = new Intent(context, PhoneDataTaskReceiver.class);
        //        i.putExtra("userId", userId);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.set(AlarmManager.ELAPSED_REALTIME, interval, pi);
    }
}
