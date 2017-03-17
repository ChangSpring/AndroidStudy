package com.alfred.androidstudy.mvp.presenter;

/**
 * 业务逻辑的接口,所有的登录业务逻辑抽象出来,写成接口
 * Created by Alfred on 2017/3/17.
 */

public interface ILoginPresenter {
    void clear();

    void doLogin(String name, String password);

    void setProgressBarVisibility(int visibility);

}
