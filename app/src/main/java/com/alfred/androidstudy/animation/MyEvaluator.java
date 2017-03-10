package com.alfred.androidstudy.animation;

import android.animation.TypeEvaluator;

/**
 * 设置当前进度
 * Created by Alfred on 2017/2/9.
 */

public class MyEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        return (int)(200 + startValue + fraction * (endValue - startValue));
    }
}
