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
                chatMessageViewHolder.setText(chatMessage.getMatter());

            }
        };
        recycler.setAdapter(mAdapter);
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        private final CircleImageView mNameField;
        private final TextView mTextField;

        public PostHolder(View itemView) {
            super(itemView);
            mNameField = (CircleImageView) itemView.findViewById(R.id.img);
            mTextField = (TextView) itemView.findViewById(R.id.text);
        }



        public void setText(String text) {
            mTextField.setText(text);
        }
    }
}
