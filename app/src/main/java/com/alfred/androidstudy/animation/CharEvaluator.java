package com.alfred.androidstudy.animation;

import android.animation.TypeEvaluator;

/**
 * Created by Alfred on 2017/2/10.
 */

public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt = (int) startValue;
        int endInt = (int) endValue;
        int curInt = (int) (startInt + fraction * (endInt - startInt));

        return (char) curInt;
    }
}
