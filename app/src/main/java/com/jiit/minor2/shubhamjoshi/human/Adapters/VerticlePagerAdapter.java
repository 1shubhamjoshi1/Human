package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VerticlePagerAdapter extends PagerAdapter {



    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Post> mPosts;
    public VerticlePagerAdapter(Context context,ArrayList<Post> posts) {
        mContext = context;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mPosts = posts;

    }

    boolean c=false;
    @Override
    public int getCount() {
     return mPosts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        c=true;
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);
        ImageView postImage = (ImageView)itemView.findViewById(R.id.mainImage);
        TextView label = (TextView) itemView.findViewById(R.id.textView);
        label.setText(mPosts.get(position).getHeading());
        Picasso.with(mContext).load(mPosts.get(position).getImageUrl()).into(postImage);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        c=true;
        container.removeView((LinearLayout) object);
    }
}