package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jiit.minor2.shubhamjoshi.human.Adapters.VerticlePagerAdapter;
import com.jiit.minor2.shubhamjoshi.human.Pagers.VerticalViewPager;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.jiit.minor2.shubhamjoshi.human.modals.Twitter.TwitterData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class NewsFeed extends AppCompatActivity {


    VerticalViewPager verticalViewPager;
    ArrayList<String> datas = new ArrayList<String>();
    private String email;
    private ArrayList<Post> wlist = new ArrayList<>();
    ConfigurationBuilder cb;
    List<TwitterData> mTwitterDatas = new ArrayList<>();
    Set<String> likes = new HashSet<>();
    String results="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticleViewPager);

        SharedPreferences prefs = getSharedPreferences("EMAIL", MODE_PRIVATE);
        String email = prefs.getString("email", "NULL");

        if (email != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }


        //data of email got


        Firebase mref = new Firebase(Constants.BASE_URL);

        Log.e("SJ",email);

        mref.child("interests").child("joshihacked@yahoo,in").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap<String, String> m = (HashMap<String, String>) snapshot.getValue();
                likes = m.keySet();

                for (String s : likes) {
                    datas.add("#" + s.trim());

                }

                for(int i=0;i<datas.size()-1;i++) {
                    results += datas.get(i) + " OR ";
                }

                results += datas.get(datas.size()-1);

                Log.e("results",results);
                new Fun().execute();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });








    }


    public class Fun extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            //  likeQuery="#fun OR #cool OR #srk OR #shubham OR #india";

            cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("t437OESAlLD5BWcbDJbkh7SC6")
                    .setOAuthConsumerSecret("YpKHZJritr8nhDYfuMWq7nq0Y8oFPmXja9Y9Zjz2n5X2JFfosS")
                    .setOAuthAccessToken("1547849809-I3uRGjJpRyppymN3bH0XAKRbfMpPTVIYw1i68zJ")
                    .setOAuthAccessTokenSecret("2ZsKxIvI6PhMQxdhaKOShnjVcEZaNtlIVuKCl7G3O9RIV");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            Log.e("SJ",datas.toString());



            try {
                Query query;
                if(results!="")
                query = new Query(results);
                else
                query= new Query("#srk");


                query.setCount(10);
                QueryResult result;
                result = twitter.search(query);
                List<twitter4j.Status> tweets = result.getTweets();

                Firebase f = new Firebase(Constants.BASE_URL).child("posts1");
               // f.removeValue();
                for (twitter4j.Status tweet : tweets) {
                    MediaEntity[] media = tweet.getMediaEntities(); //get the media entities from the status
                    for (MediaEntity m : media) { //search trough your entities

                        Date date = tweet.getCreatedAt();
                        System.out.println("Today is " +date.getTime());
                        TwitterData td = new TwitterData(m.getMediaURL(), tweet.getText(),date.getTime());
                        mTwitterDatas.add(td);

                    }
                    for(int i=0;i<mTwitterDatas.size();i++)
                    {
                        if(mTwitterDatas.get(i).getImageUrl()!=null)
                        {

                            Post p = new Post(tweet.getUser().getScreenName(),mTwitterDatas.get(i).getImageUrl(),tweet.getUser().getProfileImageURL()
                                           ,mTwitterDatas.get(i).getTag() ,-1*mTwitterDatas.get(i).getTimeStamp(),0);
                            f.push().setValue(p);
                        }
                    }
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }

                f.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {

                            Post post = child.getValue(Post.class);  wlist.add(post);
                            Log.e("task",post.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                verticalViewPager.setAdapter(new VerticlePagerAdapter(NewsFeed.this,wlist));


                            }
                        });



                    }



                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });





                //   Log.e("SJS",mTwitterDatas.size()+" S");
            } catch (TwitterException te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
                System.exit(-1);
            }
            return null;
        }

    }


}