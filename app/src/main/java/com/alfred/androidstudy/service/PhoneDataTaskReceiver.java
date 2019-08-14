package com.alfred.androidstudy.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author :  Alfred
 * @date : 2019-08-12 10:58
 */
public class PhoneDataTaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PhoneDataTaskReceiver.class);
        i.putExtra("userId", intent.getStringExtra("userId"));
        Log.i("user", intent.getStringExtra("userId"));
        context.startService(i);
        //每1分钟将接收到一次广播，这时触发service的onStartCommand()执行需要的重复操作
    }
}
