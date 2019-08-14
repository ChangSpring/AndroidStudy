package com.alfred.androidstudy;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import com.alfred.androidstudy.util.RSAUtil;

import java.security.KeyPair;

/**
 * @author :  Alfred
 * @date : 2019/1/30 16:39
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.textView1)
    TextView textView1;

    @BindView(R.id.textView2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String test = "adsfj";
        KeyPair keyPair = RSAUtil.generateRSAKeyPair();
        String encryptData = RSAUtil.encryptData(test, keyPair.getPublic());
        textView1.setText("用公钥加密:" + encryptData);
        String decryptData = RSAUtil.decryptData(encryptData, keyPair.getPrivate());
        textView2.setText("用私钥解密:" + decryptData);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }
}
