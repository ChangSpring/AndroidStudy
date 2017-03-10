package com.alfred.androidstudy.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2017/2/28.
 */

public class ViewPaperActivity extends AppCompatActivity {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    View mView1,mView2,mView3;
    List<View> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ButterKnife.bind(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        mView1 = layoutInflater.inflate(R.layout.item1_view_pager,null);
        mView2 = layoutInflater.inflate(R.layout.item2_view_pager,null);
        mView3 = layoutInflater.inflate(R.layout.item3_view_pager,null);

        mViewList.add(mView1);
        mViewList.add(mView2);
        mViewList.add(mView3);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));
                return mViewList.get(position);
            }
        };

        mViewPager.setAdapter(pagerAdapter);

    }
}
