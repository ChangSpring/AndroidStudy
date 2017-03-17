package com.alfred.androidstudy.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alfred.androidstudy.R;
import com.alfred.androidstudy.mvp.presenter.ILoginPresenter;
import com.alfred.androidstudy.mvp.presenter.ILoginPresenterCompl;
import com.alfred.androidstudy.mvp.view.ILoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 运用MVP模式实现登录功能
 */
public class LoginActivity extends AppCompatActivity implements ILoginView,View.OnClickListener {
    @Bind(R.id.et_account_login)
    EditText accountEt;
    @Bind(R.id.et_pwd_login)
    EditText pwdEt;
    @Bind(R.id.btn_login_login)
    Button loginBtn;
    @Bind(R.id.btn_clear_login)
    Button clearBtn;
    @Bind(R.id.pb_load_login)
    ProgressBar loadPb;

    ILoginPresenter mILoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mILoginPresenter = new ILoginPresenterCompl(this);
        mILoginPresenter.setProgressBarVisibility(View.INVISIBLE);

    }

    @OnClick({R.id.btn_clear_login, R.id.btn_login_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear_login:
                break;
            case R.id.btn_login_login:
                break;
            default:
                break;
        }
    }

    @Override
    public void onClearText() {
        accountEt.setText("");
        pwdEt.setText("");
    }

    @Override
    public void onLoginResult(boolean isSuccess, int code) {
        mILoginPresenter.setProgressBarVisibility(View.INVISIBLE);
        loginBtn.setEnabled(true);
        clearBtn.setEnabled(true);
        if (isSuccess){
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "failed code = " + code, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        loadPb.setVisibility(visibility);
    }
}
