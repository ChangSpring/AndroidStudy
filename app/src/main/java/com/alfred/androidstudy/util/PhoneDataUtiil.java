package com.alfred.androidstudy.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import com.alfred.androidstudy.bean.AppBean;
import com.alfred.androidstudy.bean.CallHistoryBean;
import com.alfred.androidstudy.bean.ContactBean;
import com.alfred.androidstudy.bean.SMSBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  Alfred
 * @date : 2019-08-14 16:39
 */
public class PhoneDataUtiil {

    /**
     * 获取通讯录列表
     *
     * @return
     */
    public static List<ContactBean> getContactList(Context context) {
        Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        List<ContactBean> contactList = new ArrayList<>();
        Cursor dataCursor;
        Cursor rawContactsCursor = null;
        try {
            // 从raw_contacts中读取所有联系人的id("contact_id")
            rawContactsCursor = context.getContentResolver().query(rawContactsUri, new String[]{"contact_id"}, null, null, null);
            if (rawContactsCursor != null) {
                while (rawContactsCursor.moveToNext()) {
                    String contactId = rawContactsCursor.getString(0);
                    // 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
                    dataCursor = context.getContentResolver().query(dataUri, new String[]{"data1", "mimetype"}, "contact_id=?", new String[]{contactId}, null);
                    if (dataCursor != null) {
                        ContactBean contactBean = new ContactBean();
                        // 号码
                        StringBuffer phone = new StringBuffer();
                        while (dataCursor.moveToNext()) {
                            // 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
                            String mimeType = dataCursor.getString(1);
                            if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
                                //手机号码
                                phone = phone.append(dataCursor.getString(0)).append(",");
                                contactBean.setPhone(phone.toString());
                            } else if ("vnd.android.cursor.item/name".equals(mimeType)) {
                                //联系人名字
                                contactBean.setName(dataCursor.getString(0));
                            }
                        }
                        contactList.add(contactBean);
                        dataCursor.close();
                    }
                }
            }
            Log.i("alfred", new Gson().toJson(contactList));
            return contactList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rawContactsCursor != null) {
                rawContactsCursor.close();
            }
        }
        return null;
    }

    /**
     * sms主要结构：
     * _id：短信序号，如100
     * thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
     * address：发件人地址，即手机号，如+8613811810000
     * person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
     * date：日期，long型，如1256539465022，可以对日期显示格式进行设置
     * protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
     * read：是否阅读0未读，1已读
     * status：短信状态-1接收，0complete,64pending,128failed
     * type：短信类型1是接收到的，2是已发出
     * body：短信具体内容
     * service_center：短信服务中心号码编号，如+8613800755500
     * <p>
     * String number = cur.getString(cur.getColumnIndex("想获得的属性")); //获取方法
     */
    private static final Uri SMS_INBOX = Uri.parse("content://sms/");

    public static List<SMSBean> getSMSMessageList(Context context) {
        List<SMSBean> smsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            cursor = contentResolver.query(SMS_INBOX, projection, null, null, "date desc");
            if (cursor == null) {
                return null;
            }
            while (cursor.moveToNext()) {
                SMSBean smsBean = new SMSBean();
                smsBean.setContent(cursor.getString(cursor.getColumnIndex("body")));
                smsBean.setSenderName(cursor.getString(cursor.getColumnIndex("person")));
                smsBean.setSenderPhone(cursor.getString(cursor.getColumnIndex("address")));
                smsBean.setReceiverDate(String.valueOf(cursor.getLong(cursor.getColumnIndex("date"))));

                smsList.add(smsBean);
            }
            Log.i("alfred", "sms = " + new Gson().toJson(smsList));
            return smsList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 获取通话历史记录
     *
     * @return
     */
    public static List<CallHistoryBean> getCallHistoryList(Context context) {
        List<CallHistoryBean> callHistoryList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                    new String[]{
                            CallLog.Calls.CACHED_NAME,  //姓名
                            CallLog.Calls.NUMBER,    //号码
                            CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                            CallLog.Calls.DATE,  //拨打时间
                            CallLog.Calls.DURATION,   //通话时长
                    }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            int i = 0;
            if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); (!cursor.isAfterLast()) && i < Integer.MAX_VALUE; cursor.moveToNext(), i++) {
                    CallHistoryBean callHistoryBean = new CallHistoryBean();
                    String callName = cursor.getString(0);  //名称
                    String callNumber = cursor.getString(1);  //号码
                    //如果名字为空，在通讯录查询一次有没有对应联系人
                    if (TextUtils.isEmpty(callName)) {
                        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME};
                        //设置查询条件
                        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + callNumber + "'";
                        Cursor cursor2 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols, selection, null, null);
                        int nameFieldColumnIndex = cursor2.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToFirst();
                            callName = cursor2.getString(nameFieldColumnIndex);
                        }
                        cursor2.close();
                    }
                    //号码
                    callHistoryBean.setContraryPhone(callNumber);
                    callHistoryBean.setContraryName(callName);
                    callHistoryBean.setCallDate(cursor.getString(3));
                    //秒
                    callHistoryBean.setCallDuration(cursor.getString(4));

                    callHistoryList.add(callHistoryBean);
                    //通话类型
                    //                    int callType = Integer.parseInt(cursor.getString(2));
                    //                    String callTypeStr;
                    //                    switch (callType) {
                    //                        case CallLog.Calls.INCOMING_TYPE:
                    //                            callTypeStr = CallLogInfo.CALLIN;
                    //                            break;
                    //                        case CallLog.Calls.OUTGOING_TYPE:
                    //                            callTypeStr = CallLogInfo.CALLOUT;
                    //                            break;
                    //                        case CallLog.Calls.MISSED_TYPE:
                    //                            callTypeStr = CallLogInfo.CAllMISS;
                    //                            break;
                    //                        default:
                    //                            //其他类型的，例如新增号码等记录不算进通话记录里，直接跳过
                    //                            i--;
                    //                            continue;
                    //                    }
                }
                Log.i("alfred", "call history = " + new Gson().toJson(callHistoryList));
                return callHistoryList;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 获取手机中软件安装列表
     */
    public static List<AppBean> getAppList(Context context) {
        List<AppBean> appList = new ArrayList<>();
        try {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
            if (packageInfoList != null && !packageInfoList.isEmpty()) {
                for (PackageInfo packageInfo : packageInfoList) {
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        // 非系统应用
                        AppBean appBean = new AppBean();
                        appBean.setPackageName(packageInfo.packageName);
                        appBean.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                        appBean.setVersionName(packageInfo.versionName);
                        appList.add(appBean);
                    }
                }
            }

            for (AppBean appBean : appList) {
                Log.i("alfred", appBean.toString() + "\n");
            }
            Log.i("alfred", new Gson().toJson(appList) + "\n");
            return appList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    class CallLogInfo {
        public static final String CALLIN = "call_in";
        public static final String CALLOUT = "call_out";
        public static final String CAllMISS = "call_miss";
    }

}
