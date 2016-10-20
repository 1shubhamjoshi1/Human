package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jiit.minor2.shubhamjoshi.human.App.NewsFeed;
import com.jiit.minor2.shubhamjoshi.human.NavBar;

/**
 * Created by Shubham Joshi on 16-10-2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new NavBar();

            case 1:
                return new NewsFeed();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
