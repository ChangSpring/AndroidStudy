package com.alfred.androidstudy.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author :  Alfred
 * @date : 2019-08-13 15:25
 */
public class SMSBean implements Serializable {

    /**
     * 短信内容
     */
    private String content;
    /**
     * 发送者姓名
     */
    private String senderName;
    /**
     * 发送者电话号码
     */
    private String senderPhone;
    /**
     * 收到时间
     */
    private String receiverDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(String receiverDate) {
        this.receiverDate = receiverDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "content = " + content + " senderName = " + senderName + " senderPhone = " + senderPhone + " receiverDate = " + receiverDate;
    }
}
