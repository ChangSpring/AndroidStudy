package com.alfred.androidstudy.service;//package app.framework.data;
//
//import android.app.job.JobParameters;
//import android.app.job.JobService;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//import android.widget.Toast;
//
///**
// * @author :  Alfred
// * @date : 2019-08-10 21:54
// */
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class PhoneDataService26 extends JobService {
//    public static  int JOB_ID = 10001;
//
//    @Override
//    public void onCreate() {
//        Log.i("zhangquan", "onCreate");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i("zhangquan", "onStartCommand");
//
//        return START_NOT_STICKY;
//    }
//
//    /**
//     * Job开始的时候的回调，实现实际的工作逻辑
//     *
//     * @param params
//     * @return 返回true，表示该工作耗时，同时工作处理完成后需要调用onStopJob销毁（jobFinished）那么系统假设任务是需要一些时间并且是需要在我们自己应用执行的。
//     * 如果返回值为true，我们需要手动调用jobFinished来停止该任务
//     * 返回false，任务运行不需要很长时间，到return时已完成任务处理
//     */
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        Log.i("zhangquan", "onStartJob");
//        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
//        return true;
//    }
//
//    /**
//     * 停止该Job。当JobScheduler发觉该Job条件不满足的时候，或者job被抢占被取消的时候的强制回调
//     *
//     * @param params
//     * @return 如果想在这种意外的情况下让Job重新开始，返回值应该设置为true
//     */
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        Log.i("zhangquan", "onStopJob");
//        mJobHandler.removeMessages(1);
//        //有且仅有onStartJob返回值为true时，才会调用onStopJob来销毁job
//        // 返回false来销毁这个工作
//        return false;
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.i("zhangquan", "onDestroy");
//    }
//
//    // 创建一个handler来处理对应的job
//    private Handler mJobHandler = new Handler(new Handler.Callback() {
//        // 在Handler中，需要实现handleMessage(Message msg)方法来处理任务逻辑。
//        @Override
//        public boolean handleMessage(Message msg) {
//            Toast.makeText(getApplicationContext(), "JobService task running", Toast.LENGTH_SHORT).show();
//            // 调用jobFinished
//            jobFinished((JobParameters) msg.obj, false);
//            return true;
//        }
//    });
//
//
//}
