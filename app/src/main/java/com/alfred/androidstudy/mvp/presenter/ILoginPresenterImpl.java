package com.alfred.androidstudy.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.alfred.androidstudy.mvp.model.IUser;
import com.alfred.androidstudy.mvp.model.User;
import com.alfred.androidstudy.mvp.view.ILoginView;


/**
 * 登录业务逻辑的实现类,此类实现业务逻辑的接口
 * 包含IView的对象,实现对UI的设置
 * Created by Alfred on 2017/3/17.
 */

public class ILoginPresenterImpl implements ILoginPresenter{
    private ILoginView mILoginView;
    private IUser mIUser;
    private Handler mHandler;

    public ILoginPresenterImpl(ILoginView iLoginView){
        this.mILoginView = iLoginView;
        initUser();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        mIUser = new User("zhangquan","123456");
    }

    @Override
    public void clear() {
        mILoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        boolean isLoginSuccess = true;
        final int code = mIUser.checkUserValidity(name,password);
        if (code != 0){
            isLoginSuccess = false;
        }
        final boolean result = isLoginSuccess;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mILoginView.onLoginResult(result,code);
            }
        },5000);

    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        mILoginView.onSetProgressBarVisibility(visibility);
    }
}
