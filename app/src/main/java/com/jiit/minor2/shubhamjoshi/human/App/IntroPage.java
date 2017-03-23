package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.jiit.minor2.shubhamjoshi.human.FeedStarUp;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Chat;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.hendraanggrian.circularrevealanimator.CircularRevealAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class IntroPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        ImageView i = (ImageView)findViewById(R.id.click);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularRevealAnimator.of(IntroPage.this).startActivity(new Intent(IntroPage.this, Register.class), R.id.click);
            }
        });

         final Firebase mRef = new Firebase(Constants.BASE_URL).child("posts1");


        RecyclerView recycler = (RecyclerView) findViewById(R.id.messages_recycler);

      //  recycler.setItemAnimator(new FadeInUpAnimator());

        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycler.setLayoutManager(mLayoutManager);

        recycler.setHasFixedSize(false);


        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(Post.class, R.layout.intro_item_list, PostHolder.class, mRef.orderByChild("timestamp")) {
            @Override
            public void populateViewHolder(PostHolder chatMessageViewHolder, Post chatMessage, int position) {
                Picasso.with(getBaseContext()).load(chatMessage.getImageUrl()).into(chatMessageViewHolder.mNameField);
                chatMessageViewHolder.setDate(chatMessage.getTimestamp()*-1);
                chatMessageViewHolder.setText(chatMessage.getMatter());

            }
        };
        recycler.setAdapter(mAdapter);
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        private final CircleImageView mNameField;
        private final TextView mTextField;
        private final TextView mdate;

        public PostHolder(View itemView) {
            super(itemView);
            mNameField = (CircleImageView) itemView.findViewById(R.id.img);
            mTextField = (TextView) itemView.findViewById(R.id.text);
            mdate = (TextView)itemView.findViewById(R.id.date);
        }

        public void setDate(long l)
        {
            mdate.setText(getTimeAgo(l));
        }

        public void setText(String text) {
            mTextField.setText(text);
        }
    }


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "1m ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + "m ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "1h ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "h ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + "d ago";
        }
    }
}
