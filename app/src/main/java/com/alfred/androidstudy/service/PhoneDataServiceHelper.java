package com.alfred.androidstudy.service;

import android.content.Context;

/**
 * @author :  Alfred
 * @date : 2019-08-12 12:02
 */
public class PhoneDataServiceHelper {

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //    private static void schedule(Context context) {
    //        Log.i("zhangquan", "schedule");
    //        JobInfo.Builder builder = new JobInfo.Builder(PhoneDataService26.JOB_ID++, new ComponentName(context, PhoneDataService26.class));
    //
    //        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    //        //表示应在给定时间间隔内定期运行作业,直到其被明确取消 最小间隔15分钟
    //        builder.setPeriodic(5000);
    //        //设置设备重启时,如何启动计划作业
    //        //true 由framework自动启动计划作业
    //        //false 由应用手动启动计划作业(默认)
    //        builder.setPersisted(true);
    //        // 描述作业运行必备的网络条件
    //        //NETWORK_TYPE_NONE = 0 默认值,表示作业不需要网络访问
    //        // NETWORK_TYPE_ANY = 1 需要网络连接
    //        // NETWORK_TYPE_UNMETERED = 2 必须是wifi网络状态,否则不会触发作业
    //        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
    //        //作业运行,必须时在设备充电状态
    //        //适合较少执行,但是比较耗电的任务
    //        //        builder.setRequiresCharging(false);
    //        //        //作业运行,必须时在设备未激活或睡眠状态
    //        //        //适合较少执行,但是比较耗电的任务
    //        //        builder.setRequiresDeviceIdle(false);
    //        //        //设置失败后重试间隔时间和策略
    //        //        //BACKOFF_POLICY_LINEAR: initial_backoff_millis *      num_failures 延迟时间等于乘以次数
    //        //        //BACKOFF_POLICY_EXPONENTIAL:initial_backoff_millis * 2 ^    (num_failures - 1)  延迟时间等于乘以2的次数幂
    //        //        //设置对于失败的作业如何何时重新计划使其再次运行
    //        //        //如:在资源(网络访问等)临时不可用时尽可能减少不必要的重试次数
    //        //        builder.setBackoffCriteria(TimeUnit.MINUTES.toMillis(10), JobInfo.BACKOFF_POLICY_LINEAR);
    //        //这两个方法跟setPeriodic()方法相违背
    //        //任务最少延迟时间，同时设置会抛异常IllegalArgumentException
    //        //        builder.setMinimumLatency(5000);
    //        //任务deadline，当到期没达到指定条件也会开始执行
    //        //        builder.setOverrideDeadline(1000 * 10);
    //        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    //        //            //设置首次执行前最大延迟时间
    //        //            builder.setTriggerContentMaxDelay(1000);
    //        //        }
    //
    //        jobScheduler.schedule(builder.build());
    //    }

    public static void startService(Context context) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //            context.startService(new Intent(context, PhoneDataService26.class));
        //            schedule(context);
        //        } else {
        //            context.startService(new Intent(context, PhoneDataService.class));
        //        }
    }
}
