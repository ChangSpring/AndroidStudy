package com.alfred.androidstudy.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代码实现animation
 * <p>
 * Created by Alfred on 2017/2/8.
 */
public class AnimationCodeActivity extends AppCompatActivity {
    @BindView(R.id.btn_alpha)
    Button alphaBtn;
    @BindView(R.id.btn_scale)
    Button scaleBtn;
    @BindView(R.id.btn_translate)
    Button translateBtn;
    @BindView(R.id.btn_rotate)
    Button rotateBtn;
    @BindView(R.id.btn_set)
    Button setBtn;
    @BindView(R.id.btn_interpolater)
    Button interpolaterBtn;
    @BindView(R.id.iv_img)
    ImageView imgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_code);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_alpha)
    public void alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1500);
        imgIv.startAnimation(alphaAnimation);
    }

    @OnClick(R.id.btn_scale)
    public void scale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.3f, 0.0f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        imgIv.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_translate)
    public void translate() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation
                .RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimation.setDuration(1500);
        translateAnimation.setFillAfter(true);
        imgIv.startAnimation(translateAnimation);

        imgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationCodeActivity.this,"pic",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_rotate)
    public void rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(1500);
        imgIv.startAnimation(rotateAnimation);
    }

    @OnClick(R.id.btn_set)
    public void set() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 2.0f, 0.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(2000);
        animationSet.setFillAfter(true);

        imgIv.startAnimation(animationSet);

    }

    /**
     * AccelerateDecelerateInterpolator   在动画开始与结束的地方速率改变比较慢，在中间的时候加速
     * AccelerateInterpolator             在动画开始的地方速率改变比较慢，然后开始加速
     * AnticipateInterpolator             开始的时候向后然后向前甩
     * AnticipateOvershootInterpolator    开始的时候向后然后向前甩一定值后返回最后的值
     * BounceInterpolator                 动画结束的时候弹起
     * CycleInterpolator                  动画循环播放特定的次数，速率改变沿着正弦曲线
     * DecelerateInterpolator             在动画开始的地方快然后慢
     * LinearInterpolator                 以常量速率改变
     * OvershootInterpolator              向前甩一定值后再回到原来位置
     */
    @OnClick(R.id.btn_interpolater)
    public void interpolator() {
        Animation interpolatorAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        interpolatorAnimation.setInterpolator(new OvershootInterpolator());
        interpolatorAnimation.setDuration(4000);
        interpolatorAnimation.setFillAfter(true);
        imgIv.startAnimation(interpolatorAnimation);
    }

}
