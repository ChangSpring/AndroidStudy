package com.alfred.androidstudy.mvp.model;

/**
 * Created by Alfred on 2017/3/17.
 */

public interface IUser {
    String getName();

    String getPassword();

    int checkUserValidity(String name, String password);
}
