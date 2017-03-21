package com.jiit.minor2.shubhamjoshi.human;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.jiit.minor2.shubhamjoshi.human.Adapters.AdapterForChooser;
import com.jiit.minor2.shubhamjoshi.human.App.NewsFeed;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Categories;
import com.jiit.minor2.shubhamjoshi.human.modals.GiantChooserModel;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chooser extends AppCompatActivity {

    private ArrayList chooserItems = new ArrayList();
    private GridLayoutManager mGridLayoutManager;
    private String pathPart;
    private ArrayList<String> likes = new ArrayList<>();
    private ArrayList<GiantChooserModel> mGiantChooserModels = new ArrayList<>();
    private FirebaseRecyclerAdapter<Categories, ChooserInterestHolder> mAdapter;
    private int countForStatus = 0;
    private String ImageUrl;


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("Email", Context.MODE_PRIVATE);
        pathPart = sp.getString("Email", "Error");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chosoer);
        final LinearLayout header = (LinearLayout) findViewById(R.id.headerChooser);
        final RelativeLayout footer = (RelativeLayout) findViewById(R.id.footerChooser);


        header.setAlpha(0f);
        footer.setAlpha(0f);

        final Firebase newRef = new Firebase(Constants.BASE_URL);



        newRef.child("post1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Post p = postSnapshot.getValue(Post.class);
                    chooserItems.add(p);
                    Log.e("SJ",p.getImageUrl());
                    GiantChooserModel giantChooserModel = new GiantChooserModel();
                    giantChooserModel.setDescription(p.getHeading());
                    giantChooserModel.setSelected(false);
                    giantChooserModel.setSubDescription(p.getMatter());
                    giantChooserModel.setUrl(p.getImageUrl());
                    mGiantChooserModels.add(giantChooserModel);
                }

                ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.chooserProgress);
                mProgressBar.setVisibility(View.INVISIBLE);
                footer.setAlpha(1f);
                header.setAlpha(1f);


                mGridLayoutManager = new GridLayoutManager(Chooser.this, 3);
                RecyclerView rView = (RecyclerView) findViewById(R.id.interest_choices_recycler_view);
                rView.setLayoutManager(mGridLayoutManager);
                AdapterForChooser mAdapterForChooser = new AdapterForChooser(Chooser.this, chooserItems, pathPart, mGiantChooserModels);
                rView.setAdapter(mAdapterForChooser);
                rView.setHasFixedSize(true);
            }

//                mAdapterForChooser.setClickListener(new AdapterForChooser.ClickListener() {
//
//                    @Override
//                    public void onClick(View v, int pos) {
//
//
//                        Firebase ref = baseRef.child("likes").child(pathPart);
//                        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.selectedBg);
//
//                        if (!mGiantChooserModels.get(pos).isSelected()) {
//                            Vibrator vibe = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
//                            vibe.vibrate(50);
//                            rl.setVisibility(View.VISIBLE);
//                            countForStatus++;
//
//                            likes.add(mGiantChooserModels.get(pos).getSubDescription());
//                            mGiantChooserModels.get(pos).setSelected(true);
//
//
//                            ref.child(mGiantChooserModels.get(pos).getSubDescription()).setValue("");
//
//
//                        } else {
//                            rl.setVisibility(View.INVISIBLE);
//                            countForStatus--;
//                            //o(n) complexity
//                            int count = Collections.frequency(likes, mGiantChooserModels.get(pos).getSubDescription());
//                            if (count == 1)
//                                ref.child(mGiantChooserModels.get(pos).getSubDescription()).removeValue();
//                            mGiantChooserModels.get(pos).setSelected(false);
//                            likes.remove(mGiantChooserModels.get(pos).getSubDescription());
//                        }
//
////                        Log.e("SJS",likes.size() + "");
//
//                    }
//                });
//
//                mAdapterForChooser.notifyDataSetChanged();
//            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chooser.this, NewsFeed.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}