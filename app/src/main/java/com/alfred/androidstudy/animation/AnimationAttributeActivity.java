package com.alfred.androidstudy.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动画 alpha,scale,translate,rotate,set的xml属性以及用法
 *
 * Created by Alfred on 2017/2/8.
 */

public class AnimationAttributeActivity extends AppCompatActivity{
    @Bind(R.id.btn_alpha)
    Button alphaBtn;
    @Bind(R.id.btn_scale)
    Button scaleBtn;
    @Bind(R.id.btn_rotate)
    Button rotateBtn;
    @Bind(R.id.btn_translate)
    Button translateBtn;
    @Bind(R.id.btn_set)
    Button setBtn;
    @Bind(R.id.iv_img)
    ImageView imgIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_attribute);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_scale)
    public void scale(){
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        Animation scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scaleanim);
        imgIv.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_alpha)
    public void alpha(){
        Animation alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.alphaanim);
        imgIv.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.btn_translate)
    public void translate(){
        Animation alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.translateanim);
        imgIv.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.btn_rotate)
    public void rotate(){
        Animation rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotateanim);
        imgIv.startAnimation(rotateAnimation);
    }

    @OnClick(R.id.btn_set)
    public void set(){
        Animation setAnimation = AnimationUtils.loadAnimation(this,R.anim.setanim);
        imgIv.startAnimation(setAnimation);
    }
}
