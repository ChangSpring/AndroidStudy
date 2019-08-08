//package com.alfred.androidstudy
//
//import android.net.Uri
//import android.util.Log
//
///**
// * @author :  Alfred
// * @date   : 2019-08-08 11:34
// *
// */
//class A : BaseActivity() {
//    override fun getContentLayoutId(): Int {
//    }
//
//    private val SMS_INBOX = Uri.parse("content://sms/")
//    private var datas  = ArrayList<Map<String, Any>>()
//    private var datalistView  = ArrayList<MessageBean>()
//
//
//    fun getSmsFromPhone() {
//        val cr = contentResolver
//        val projection = arrayOf("_id", "address", "person", "body", "date", "type")
//        var cur = cr.query(SMS_INBOX, projection, null, null, "date desc")
//        if (null == cur) {
////            cur!!.close()
//            Log.i("ooc", "************cur == null")
//            return
//        }
//
//        while (cur.moveToNext()) {
//
//            val number = cur.getString(cur.getColumnIndex("address"))//手机号
//            val name = cur.getString(cur.getColumnIndex("person"))//联系人姓名列表
//            val body = cur.getString(cur.getColumnIndex("body"))//短信内容
//            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
//            if (number.isNullOrEmpty()) {
//                continue
//            }
//            val map = HashMap<String, Any>()
//            map["num"] = number + ""
//            map["mess"] = body + ""
//            //   datalistView.add(map)
//            datas.add(map)
//            MessageBean msg = MessageBean(name, number, body)
//            datalistView.add(msg)
//            Log.w("第" + cur.position, "  \"第\"+cur.position,")
//        }
//        cur.close()
//        Log.w("ccd", "关闭游标")
//        Log.w("ccd", "结束了")
//    }
//}