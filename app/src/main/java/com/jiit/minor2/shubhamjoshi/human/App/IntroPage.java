package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.jiit.minor2.shubhamjoshi.human.FeedStarUp;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Chat;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;

import io.github.hendraanggrian.circularrevealanimator.CircularRevealAnimator;

public class IntroPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        ImageView i = (ImageView)findViewById(R.id.click);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularRevealAnimator.of(IntroPage.this).startActivity(new Intent(IntroPage.this, FeedStarUp.class), R.id.click);
            }
        });

         final Firebase mRef = new Firebase(Constants.BASE_URL).child("posts1");

        RecyclerView recycler = (RecyclerView) findViewById(R.id.messages_recycler);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(Post.class, android.R.layout.two_line_list_item, PostHolder.class, mRef) {
            @Override
            public void populateViewHolder(PostHolder chatMessageViewHolder, Post chatMessage, int position) {
                chatMessageViewHolder.setName(chatMessage.getHeading());
                chatMessageViewHolder.setText(chatMessage.getMatter());
            }
        };
        recycler.setAdapter(mAdapter);
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        private final TextView mNameField;
        private final TextView mTextField;

        public PostHolder(View itemView) {
            super(itemView);
            mNameField = (TextView) itemView.findViewById(android.R.id.text1);
            mTextField = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setName(String name) {
            mNameField.setText(name);
        }

        public void setText(String text) {
            mTextField.setText(text);
        }
    }
}
