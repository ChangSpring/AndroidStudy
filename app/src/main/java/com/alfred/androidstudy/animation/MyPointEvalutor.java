package com.alfred.androidstudy.animation;

import android.animation.TypeEvaluator;

/**
 * Created by Alfred on 2017/2/10.
 */

public class MyPointEvalutor implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int startInt = startValue.getRadius();
        int endInt = endValue.getRadius();
        int curInt = (int) (startInt + fraction * (endInt - startInt));
        return new Point(curInt);
    }
}
