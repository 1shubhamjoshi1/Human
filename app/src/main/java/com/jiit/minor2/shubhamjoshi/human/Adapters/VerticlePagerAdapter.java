package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.*;
import com.firebase.client.Firebase;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
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

        try{
            ImageView postImage = (ImageView)itemView.findViewById(R.id.mainImage);
            TextView label = (TextView) itemView.findViewById(R.id.textView);
            ImageView postOwner = (ImageView)itemView.findViewById(R.id.postOwner);
            Picasso.with(mContext).load(mPosts.get(position).getImageOwnerUrl()).into(postOwner);
            TextView postData = (TextView)itemView.findViewById(R.id.postText);
            postData.setText(mPosts.get(position).getMatter());
            label.setText(mPosts.get(position).getHeading());
            Picasso.with(mContext).load(mPosts.get(position).getImageUrl()).into(postImage);
            container.addView(itemView);
            final LinearLayout holder = (LinearLayout)itemView.findViewById(R.id.holder);

            String url1 = mPosts.get(position).imageUrl;
            URL ulrn = new URL(url1);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is;
            is = con.getInputStream();

            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)

                Palette.generateAsync(bmp, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        int bgColor = palette.getDarkVibrantColor(mContext.getResources().getColor(android.R.color.black));
                        holder.setBackgroundColor(bgColor);
                    }
                });
            else
                Log.e("MyTag_BMP","The Bitmap is NULL");

        }catch (Exception e){

        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        c=true;
        container.removeView((LinearLayout) object);
    }
}