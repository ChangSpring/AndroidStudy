package com.alfred.androidstudy;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import com.alfred.androidstudy.util.AESUtil;
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

        KeyPair keyPair = RSAUtil.generateRSAKeyPair();
        byte[] encryptData = RSAUtil.encryptData(AESUtil.generateSecretAESKey().getBytes(), keyPair.getPublic());
        textView1.setText("用公钥加密:" + new String(encryptData));
        byte[] decryptData = RSAUtil.decryptData(encryptData, keyPair.getPrivate());
        textView2.setText("用私钥解密:" + new String(decryptData));


//        KeyPair keyPair2 = RSAUtil.generateRSAKeyPair();
//        String encryptData2 = RSAUtil.encryptData2(AESUtil.generateSecretAESKey(), keyPair2.getPublic());
//        textView1.setText("用公钥加密:" + encryptData2);
//        String decryptData2 = RSAUtil.decryptData2(encryptData2, keyPair2.getPrivate());
//        textView2.setText("用私钥解密:" + decryptData2);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }
}
