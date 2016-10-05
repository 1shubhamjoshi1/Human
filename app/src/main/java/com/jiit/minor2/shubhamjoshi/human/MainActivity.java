package com.jiit.minor2.shubhamjoshi.human;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jiit.minor2.shubhamjoshi.human.Adapters.HomeAdapter;
import com.jiit.minor2.shubhamjoshi.human.modals.Items;

import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity {
    ConfigurationBuilder cb;

    public void startUp() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("t437OESAlLD5BWcbDJbkh7SC6")
                .setOAuthConsumerSecret("YpKHZJritr8nhDYfuMWq7nq0Y8oFPmXja9Y9Zjz2n5X2JFfosS")
                .setOAuthAccessToken("1547849809-I3uRGjJpRyppymN3bH0XAKRbfMpPTVIYw1i68zJ")
                .setOAuthAccessTokenSecret("2ZsKxIvI6PhMQxdhaKOShnjVcEZaNtlIVuKCl7G3O9RIV");


        Twitter twitter = new TwitterFactory(cb.build()).getInstance();


        try {
            Query query = new Query("#IPL");
            query.setCount(100);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                MediaEntity[] media = tweet.getMediaEntities(); //get the media entities from the status
                for (MediaEntity m : media) { //search trough your entities
                    System.out.println(m.getMediaURL()); //get your url!
                }
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }


    private List<Items> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                startUp();
            }

            // your code

        }).start();


        String data1[] = {"a", "b"};
        String data2[] = {"a", "b"};
        test();
        HomeAdapter homeAdapter = new HomeAdapter(this, mList);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeAdapter.setClickListener(new HomeAdapter.ClickListener() {
            @Override
            public void onClick(View v, int pos) {

            }
        });

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


    }


    void test() {

        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
        mList.add(new Items("A", "B", 1));
        mList.add(new Items("AA", "BB", 0));
        mList.add(new Items("AAA", "BA", 0));
        mList.add(new Items("AV", "BC", 1));
    }
}
