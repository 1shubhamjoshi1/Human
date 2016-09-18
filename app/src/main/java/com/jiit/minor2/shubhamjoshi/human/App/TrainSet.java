package com.jiit.minor2.shubhamjoshi.human.App;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.firebase.TrainData;
import com.squareup.picasso.Picasso;

public class TrainSet extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_train_set);

        Firebase mRef = new Firebase(Constants.BASE_URL + "/trainData");

        TrainData trainData = new TrainData("Music", "Music", "1", "http://jiitminor128.netai.net/pictures/15.JPG");
        mRef.push().setValue(trainData);


        RecyclerView r = (RecyclerView) findViewById(R.id.trainRecyclerView);
        r.setLayoutManager(new GridLayoutManager(this, 2));
        r.setHasFixedSize(true);

        FirebaseRecyclerAdapter<TrainData, ChatHolder> f = new FirebaseRecyclerAdapter<TrainData, ChatHolder>(TrainData.class, R.layout.train_list_item, ChatHolder.class, mRef) {
            @Override
            protected void populateViewHolder(ChatHolder chatHolder, TrainData train, int i) {
                chatHolder.setName(train.getCategory());
                chatHolder.setText(train.getHeading());
                chatHolder.setImage(train.getImageUrl());
            }
        };

        r.setAdapter(f);

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView field;
        TextView sub;
        ImageView image;

        public ChatHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            field = (TextView) mView.findViewById(R.id.i1);
            field.setText(name);
        }

        public void setText(String text) {
            sub = (TextView) mView.findViewById(R.id.i2);
            sub.setText(text);
        }

        public void setImage(String str) {
            image = (ImageView) mView.findViewById(R.id.image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Firebase newBranch = new Firebase(Constants.BASE_URL + "/users");
                    newBranch.setValue(field.getText() + " " + sub.getText());
                }
            });
            Picasso.with(image.getContext()).load(str).into(image);
        }
    }
}
