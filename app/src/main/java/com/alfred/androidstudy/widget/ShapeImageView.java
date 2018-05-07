package com.alfred.androidstudy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by alfred on 2018/1/13.
 */

public class ShapeImageView extends AppCompatImageView {
    public ShapeImageView(Context context) {
        this(context,null);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public ShapeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
