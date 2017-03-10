package com.alfred.androidstudy.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimatorXMLActivity extends AppCompatActivity {
    @Bind(R.id.menu)
    Button menuBtn;
    @Bind(R.id.item1)
    Button item1Btn;
    @Bind(R.id.item2)
    Button item2Btn;
    @Bind(R.id.item3)
    Button item3Btn;
    @Bind(R.id.item4)
    Button item4Btn;
    @Bind(R.id.item5)
    Button item5Btn;

    private int openRadius = 500;

    private static final String TAG = AnimatorXMLActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_xml);

        ButterKnife.bind(this);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOnClick(item1Btn,0);
                menuOnClick(item2Btn,1);
                menuOnClick(item3Btn,2);
                menuOnClick(item4Btn,3);
                menuOnClick(item5Btn,4);
            }
        });

    }

    private void menuOnClick(View view,int index) {

//        int menuX = menuBtn.getRight() - openRadius / 2;
//        int menuY = menuBtn.getBottom() - openRadius / 2;
//
//        int topX = menuX;
//        int topY = menuY - openRadius;
//        int bottomX = menuX - openRadius;
//        int bottomY = menuY;

        if (view.getVisibility() != View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }

        int translationX = (openRadius / 4 * index) - openRadius;
        int translationY = -(openRadius / 4 * index);
        Log.i(TAG,"translationX = " + translationX + " translationY = " + translationY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view,"translationX",0,translationX),
                ObjectAnimator.ofFloat(view,"translationY",0,translationY),
                ObjectAnimator.ofFloat(view,"scaleX",0f,1f),
                ObjectAnimator.ofFloat(view,"scaleY",0f,1f),
                ObjectAnimator.ofFloat(view,"alpha",0f,1),
                ObjectAnimator.ofFloat(view,"rotation",0,-1440));

        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(1000).start();
    }

    private void doAnimtorOpen(View view, int index, int menuX) {

    }
}
