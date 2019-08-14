package com.alfred.androidstudy.bean;

/**
 * @author :  Alfred
 * @date : 2019-08-13 16:51
 */
public class CallHistoryBean {

    /**
     * 对方手机号
     */
    private String contraryPhone;
    private String contraryName;
    private String callDuration;
    private String callDate;

    public String getContraryPhone() {
        return contraryPhone;
    }

    public void setContraryPhone(String contraryPhone) {
        this.contraryPhone = contraryPhone;
    }

    public String getContraryName() {
        return contraryName;
    }

    public void setContraryName(String contraryName) {
        this.contraryName = contraryName;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }
}
