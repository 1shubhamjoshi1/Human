package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.jiit.minor2.shubhamjoshi.human.Adapters.TrainAdapter;
import com.jiit.minor2.shubhamjoshi.human.MainActivity;
import com.jiit.minor2.shubhamjoshi.human.Pagers.VerticalViewPager;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Twitter.TwitterData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TrainSet extends AppCompatActivity {

    String email;

    RecyclerView mRecyclerView;
    Firebase mRef;
    TrainAdapter.ClickListener mClickListener;
    ConfigurationBuilder cb;
    List<TwitterData> mTwitterDatas = new ArrayList<>();

    public void startUp() {
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("t437OESAlLD5BWcbDJbkh7SC6")
                .setOAuthConsumerSecret("YpKHZJritr8nhDYfuMWq7nq0Y8oFPmXja9Y9Zjz2n5X2JFfosS")
                .setOAuthAccessToken("1547849809-I3uRGjJpRyppymN3bH0XAKRbfMpPTVIYw1i68zJ")
                .setOAuthAccessTokenSecret("2ZsKxIvI6PhMQxdhaKOShnjVcEZaNtlIVuKCl7G3O9RIV");


        Twitter twitter = new TwitterFactory(cb.build()).getInstance();


        try {
            Query query = new Query("#USA OR #India OR #dark");
            query.setCount(100);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            for (Status tweet : tweets) {
                MediaEntity[] media = tweet.getMediaEntities(); //get the media entities from the status
                for (MediaEntity m : media) { //search trough your entities
                    System.out.println(m.getMediaURL()); //get your url!
                    TwitterData td = new TwitterData(m.getMediaURL(), tweet.getText());
                    mTwitterDatas.add(td);
                }
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView r = (RecyclerView) findViewById(R.id.trainRecyclerView);
                    TrainAdapter trainAdapter = new TrainAdapter(TrainSet.this, mTwitterDatas);

                    r.setAdapter(trainAdapter);
                    r.setLayoutManager(new GridLayoutManager(TrainSet.this, 2));
                    Log.e("SJ", mTwitterDatas.size() + " S");
                    Log.e("SSSS",email+"SSS");
                    trainAdapter.setClickListener(new TrainAdapter.ClickListener() {

                        @Override
                        public void onClick(View v, int pos) {
                            Firebase f = new Firebase(Constants.BASE_URL);

                            //lets strip data
                            ImageView iy = (ImageView) v.findViewById(R.id.mask);
                            if (mTwitterDatas.get(pos).getSelected() == 0) {
                                iy.setVisibility(View.VISIBLE);
                                mTwitterDatas.get(pos).setSelected(1);
                            } else {
                                iy.setVisibility(View.INVISIBLE);
                                mTwitterDatas.get(pos).setSelected(0);
                            }
                            String str = mTwitterDatas.get(pos).getTag();
                            List<String> list = new LinkedList<String>(Arrays.asList(str.split(" ")));
                            List<String> newList = new LinkedList<String>();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).contains("#"))
                                    newList.add(list.get(i));

                            }


                            Log.e("SJ", newList.toString());


                            for (int i = 0; i < newList.size(); i++) {
                                f.child("interests").child(email.replace(".", ",")).child(newList.get(i).replace("#", " ").replace(".", "")).setValue("");
                            }


                        }
                    });
                }
            });


            //   Log.e("SJS",mTwitterDatas.size()+" S");
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_train_set);
        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NewsFeed.class));
            }
        });

        showCase();

    }


    void showCase() {
        ShowcaseView.Builder showCaseBuilder = new ShowcaseView.Builder(this);

        showCaseBuilder.setTarget(Target.NONE);
        showCaseBuilder.setContentTitle("Lets get you started");
        showCaseBuilder.setContentText("Let our system get trained to know\n more about you!!");
        showCaseBuilder.build();
    }

    @Override
    protected void onStart() {

        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startUp();
            }
        }).start();
        SharedPreferences prefs = getSharedPreferences("EMAIL", MODE_PRIVATE);
        email = prefs.getString("email", null);
        Log.e("SJ",email+"SS");
        if (email != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }
    }




}
