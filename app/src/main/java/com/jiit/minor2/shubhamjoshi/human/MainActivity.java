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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    MaterialSearchView searchView;
    private List<Items> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rView);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        initSearchView();
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



    private void initSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setCursorDrawable(R.drawable.custom_search_cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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
