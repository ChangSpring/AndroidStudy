package com.alfred.androidstudy.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2017/4/25.
 */

public class DesignActivity extends AppCompatActivity {
    @BindView(R.id.btn_snackbar_design)
    Button snackbarBtn;
    @BindView(R.id.til_pwd_design)
    TextInputLayout pwdTil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        ButterKnife.bind(this);

        snackbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"snackbar comes out",Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DesignActivity.this,"I'm a snackbarer !",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });


        EditText editText = pwdTil.getEditText();
        pwdTil.setHint("password");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 4){
                    pwdTil.setError("Password error");
                    pwdTil.setErrorEnabled(true);
                }else{
                    pwdTil.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
