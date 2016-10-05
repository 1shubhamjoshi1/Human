package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiit.minor2.shubhamjoshi.human.R;

public class VerticlePagerAdapter extends PagerAdapter {

    String mResources[] = {"S","B","C","D","Edsfsflasjfldsjfajfldsjflasdjfladsfljadslfjad;fjadsjf;ladsfjasl;fjdasjfldasjflsdjfldsjflas;jfadjlfjsal;fjlads;jfladjf;asjfla;jfl;adjf;lasjfladsjfjadl;jfjfadsjfadsjfl;"};

    Context mContext;
    LayoutInflater mLayoutInflater;

    public VerticlePagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);

        TextView label = (TextView) itemView.findViewById(R.id.textView);
        label.setText(mResources[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}