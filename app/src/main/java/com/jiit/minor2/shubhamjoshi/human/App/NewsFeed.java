package com.jiit.minor2.shubhamjoshi.human.App;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jiit.minor2.shubhamjoshi.human.Adapters.VerticlePagerAdapter;
import com.jiit.minor2.shubhamjoshi.human.Pagers.VerticalViewPager;
import com.jiit.minor2.shubhamjoshi.human.R;

public class NewsFeed extends AppCompatActivity {


    VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticleViewPager);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this));
    }


}
