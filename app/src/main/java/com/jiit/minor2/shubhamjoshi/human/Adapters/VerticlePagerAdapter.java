package com.jiit.minor2.shubhamjoshi.human.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class VerticlePagerAdapter extends PagerAdapter {


    Context mContext;
    String hintt;
    LayoutInflater mLayoutInflater;
    ArrayList<Post> mPosts;

    public VerticlePagerAdapter(Context context, ArrayList<Post> posts,String hintt) {
        mContext = context;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mPosts = posts;
        this.hintt = hintt;

    }

    boolean c = false;

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
        c = true;
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);


        ImageView postImage = (ImageView) itemView.findViewById(R.id.mainImage);
        TextView label = (TextView) itemView.findViewById(R.id.textView);
        ImageView postOwner = (ImageView) itemView.findViewById(R.id.postOwner);
        Picasso.with(mContext).load(mPosts.get(position).getImageOwnerUrl().replace("_normal", "")).into(postOwner);
        TextView postData = (TextView) itemView.findViewById(R.id.postText);
        postData.setText(mPosts.get(position).getMatter());
        label.setText(mPosts.get(position).getHeading());
        Picasso.with(mContext).load(mPosts.get(position).getImageUrl()).into(postImage);
        container.addView(itemView);
        final TextView hint = (TextView) itemView.findViewById(R.id.hint);
        hint.setText("You have shown interest in "+hintt);
        Picasso.with(mContext).load(mPosts.get(position).getImageUrl())
                .transform(new Blur(mContext, 50)).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.blurBg));

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        c = true;
        container.removeView((LinearLayout) object);
    }

    public class Blur implements Transformation {
        protected static final int UP_LIMIT = 25;
        protected static final int LOW_LIMIT = 1;
        protected final Context context;
        protected final int blurRadius;


        public Blur(Context context, int radius) {
            this.context = context;

            if (radius < LOW_LIMIT) {
                this.blurRadius = LOW_LIMIT;
            } else if (radius > UP_LIMIT) {
                this.blurRadius = UP_LIMIT;
            } else
                this.blurRadius = radius;
        }


        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap sourceBitmap = source;

            Bitmap blurredBitmap;
            blurredBitmap = source.copy(source.getConfig(), true);

            RenderScript renderScript = RenderScript.create(context);

            Allocation input = Allocation.createFromBitmap(renderScript,
                    sourceBitmap,
                    Allocation.MipmapControl.MIPMAP_FULL,
                    Allocation.USAGE_SCRIPT);


            Allocation output = Allocation.createTyped(renderScript, input.getType());

            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript,
                    Element.U8_4(renderScript));

            script.setInput(input);
            script.setRadius(blurRadius);

            script.forEach(output);
            output.copyTo(blurredBitmap);

            source.recycle();
            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blurred";
        }

    }
}