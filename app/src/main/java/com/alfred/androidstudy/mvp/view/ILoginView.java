package com.alfred.androidstudy.mvp.view;

/**
 * 视图逻辑的接口,所有的视图逻辑业务抽象出来写成接口
 * Created by Alfred on 2017/3/17.
 */

public interface ILoginView {
    void onClearText();

    void onLoginResult(boolean isSuccess, int code);

    void onSetProgressBarVisibility(int visibility);
}
