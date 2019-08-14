package com.alfred.androidstudy.bean;

import android.support.annotation.NonNull;

/**
 * @author :  Alfred
 * @date : 2019-08-13 15:32
 */
public class ContactBean {

    private String name;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return "name = " + name + " phone = " + phone;
    }
}
