package com.alfred.androidstudy;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author :  Alfred
 * @date : 2019/1/30 16:39
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.textView1)
    TextView textView1;

    @BindView(R.id.textView2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }


    @OnClick(R.id.button1)
    void requestContactPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_CONTACTS)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        readContact();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }


    private ArrayList<HashMap<String, String>> readContact() {
        // 首先,从raw_contacts中读取联系人的id("contact_id")
        // 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
        // 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
        Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        // 从raw_contacts中读取所有联系人的id("contact_id")
        Cursor rawContactsCursor = getContentResolver().query(rawContactsUri,
                new String[]{"contact_id"}, null, null, null);
        if (rawContactsCursor != null) {
            while (rawContactsCursor.moveToNext()) {
                String contactId = rawContactsCursor.getString(0);
                // System.out.println("得到的contact_id="+contactId);
                // 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
                Cursor dataCursor = getContentResolver().query(dataUri,
                        new String[]{"data1", "mimetype"}, "contact_id=?",
                        new String[]{contactId}, null);
                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<>();
                    String data;//名字
                    StringBuffer data1 = new StringBuffer();// 号码
                    while (dataCursor.moveToNext()) {
                        String mimetype = dataCursor.getString(1);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {//手机号码
                            data1 = data1.append(dataCursor.getString(0) + ",");
                            map.put("phone", data1.toString());
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {//联系人名字
                            data = dataCursor.getString(0);
                            map.put("name", data);
                        }
                    }
                    list.add(map);
                    dataCursor.close();
                }
            }
            rawContactsCursor.close();
        }
        return list;
    }


    private Uri SMS_INBOX = Uri.parse("content://sms/");

    @OnClick(R.id.button2)
    void requestSMSPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_SMS, Permission.RECEIVE_SMS)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        obtainPhoneMessage();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    void obtainPhoneMessage() {
        List<Map<String, Object>> list = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            Map<String, Object> map = new HashMap<>(cur.getCount());
            map.put("num", number);
            map.put("mess", body);
            list.add(map);
        }
    }


    //    String number = cur.getString(cur.getColumnIndex("想获得的属性")); //获取方法 a

    /**
     * sms主要结构：
     *  _id：短信序号，如100
     *  thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
     *  address：发件人地址，即手机号，如+8613811810000
     *  person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
     *  date：日期，long型，如1256539465022，可以对日期显示格式进行设置
     *  protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
     *  read：是否阅读0未读，1已读
     *  status：短信状态-1接收，0complete,64pending,128failed
     *  type：短信类型1是接收到的，2是已发出
     *  body：短信具体内容
     *  service_center：短信服务中心号码编号，如+8613800755500
     */

    /**
     * 利用系统CallLog获取通话历史记录
     *
     * @param num 要读取记录的数量
     * @return
     */
    @OnClick(R.id.button3)
    void getCallHistoryList() {
        int num = 100;
        Cursor cs;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1000);

        }
        cs = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
                new String[]{
                        CallLog.Calls.CACHED_NAME,  //姓名
                        CallLog.Calls.NUMBER,    //号码
                        CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
                        CallLog.Calls.DATE,  //拨打时间
                        CallLog.Calls.DURATION,   //通话时长
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        int i = 0;
        if (cs != null && cs.getCount() > 0) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_today = simpleDateFormat.format(date);
            for (cs.moveToFirst(); (!cs.isAfterLast()) && i < num; cs.moveToNext(), i++) {
                String callName = cs.getString(0);  //名称
                String callNumber = cs.getString(1);  //号码
                //如果名字为空，在通讯录查询一次有没有对应联系人
                if (callName == null || callName.equals("")) {
                    String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME};
                    //设置查询条件
                    String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + "='" + callNumber + "'";
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            cols, selection, null, null);
                    int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        callName = cursor.getString(nameFieldColumnIndex);
                    }
                    cursor.close();
                }
                //通话类型
                int callType = Integer.parseInt(cs.getString(2));
                String callTypeStr = "";
                switch (callType) {
                    case CallLog.Calls.INCOMING_TYPE:
                        callTypeStr = CallLogInfo.CALLIN;
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        callTypeStr = CallLogInfo.CALLOUT;
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callTypeStr = CallLogInfo.CAllMISS;
                        break;
                    default:
                        //其他类型的，例如新增号码等记录不算进通话记录里，直接跳过
                        Log.i("ssss", "" + callType);
                        i--;
                        continue;
                }
                //拨打时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date callDate = new Date(Long.parseLong(cs.getString(3)));
                String callDateStr = sdf.format(callDate);
                if (callDateStr.equals(date_today)) { //判断是否为今天
                    sdf = new SimpleDateFormat("HH:mm");
                    callDateStr = sdf.format(callDate);
                } else if (date_today.contains(callDateStr.substring(0, 7))) { //判断是否为当月
                    sdf = new SimpleDateFormat("dd");
                    int callDay = Integer.valueOf(sdf.format(callDate));

                    int day = Integer.valueOf(sdf.format(date));
                    if (day - callDay == 1) {
                        callDateStr = "昨天";
                    } else {
                        sdf = new SimpleDateFormat("MM-dd");
                        callDateStr = sdf.format(callDate);
                    }
                } else if (date_today.contains(callDateStr.substring(0, 4))) { //判断是否为当年
                    sdf = new SimpleDateFormat("MM-dd");
                    callDateStr = sdf.format(callDate);
                }

                //通话时长
                int callDuration = Integer.parseInt(cs.getString(4));
                int min = callDuration / 60;
                int sec = callDuration % 60;
                String callDurationStr = "";
                if (sec > 0) {
                    if (min > 0) {
                        callDurationStr = min + "分" + sec + "秒";
                    } else {
                        callDurationStr = sec + "秒";
                    }
                }

                /**
                 * callName 名字
                 * callNumber 号码
                 * callTypeStr 通话类型
                 * callDateStr 通话日期
                 * callDurationStr 通话时长
                 * 请在此处执行相关UI或存储操作，之后会查询下一条通话记录
                 */
                Log.i("zhangquan", "callName = " + callName);
                Log.i("zhangquan", "callNumber = " + callNumber);
                Log.i("zhangquan", "callTypeStr = " + callTypeStr);
                Log.i("zhangquan", "callDateStr = " + callDateStr);
                Log.i("zhangquan", "callDurationStr = " + callDurationStr);
            }
        }
    }

    class CallLogInfo {
        public static final String CALLIN = "call_in";
        public static final String CALLOUT = "call_out";
        public static final String CAllMISS = "call_miss";
    }

    @OnClick(R.id.button4)
    void getThirdAppList() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        // 判断是否系统应用：
        //List<PackageInfo> apps = new ArrayList<PackageInfo>();
        List<String> thirdAPP = new ArrayList<>();
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo pak = (PackageInfo) packageInfoList.get(i);
            //判断是否为系统预装的应用
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // 第三方应用
                // apps.add(pak);
                Log.i("zhangquan", "package name = " + pak.packageName);
                thirdAPP.add(pak.packageName);
            } else {
                //系统应用
            }
        }
        //        return thirdAPP;

    }

    /**
     * 判断是否是三星的手机
     *
     * @return 是否是三星的手机
     */
    public static boolean isSamsung() {
        return getManufacturer().toLowerCase().contains("samsung");
    }

    /**
     * 获取厂商信息
     *
     * @return 获取厂商信息
     */
    public static String getManufacturer() {
        return (Build.MANUFACTURER) == null ? "" : (Build.MANUFACTURER).trim();
    }
}
