package com.jiit.minor2.shubhamjoshi.human;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jiit.minor2.shubhamjoshi.human.App.QRCode;

import io.github.hendraanggrian.circularrevealanimator.CircularRevealAnimator;

public class FeedStarUp extends FragmentActivity {

    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_star_up);
        mViewPager = (ViewPager) findViewById(R.id.activity_feed_star_up);
        PagerAdapter pagerAdapter = new com.jiit.minor2.shubhamjoshi.human.Adapters.PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(1, true);

    }

    public void funda(View view)
    {
        startActivity(new Intent(view.getContext(), QRCode.class));
    }
}
