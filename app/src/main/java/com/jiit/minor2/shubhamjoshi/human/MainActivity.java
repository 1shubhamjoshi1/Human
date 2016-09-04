package com.jiit.minor2.shubhamjoshi.human;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jiit.minor2.shubhamjoshi.human.Adapters.HomeAdapter;
import com.jiit.minor2.shubhamjoshi.human.modals.Items;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Items> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rView);


        String data1[]={"a","b"};
        String data2[]={"a","b"};
        test();
        HomeAdapter homeAdapter = new HomeAdapter(this,mList);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeAdapter.setClickListener(new HomeAdapter.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
    }




    void test()
    {

        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
        mList.add(new Items("A","B",1));
        mList.add(new Items("AA","BB",0));
        mList.add(new Items("AAA","BA",0));
        mList.add(new Items("AV","BC",1));
    }
}
