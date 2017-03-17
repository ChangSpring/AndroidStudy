package com.alfred.androidstudy.mvp.model;

/**
 * Created by Alfred on 2017/3/17.
 */

public class User implements IUser {
    private String name;
    private String password;

    public User(String name, String pwd) {
        this.name = name;
        this.password = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String password) {
        if (name == null || password == null || !name.equals(this.name) || !password.equals(this.password)) {
            return -1;
        }
        return 0;
    }
}
