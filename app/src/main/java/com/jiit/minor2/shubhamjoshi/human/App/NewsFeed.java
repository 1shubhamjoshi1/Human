package com.jiit.minor2.shubhamjoshi.human.App;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.jiit.minor2.shubhamjoshi.human.Adapters.VerticlePagerAdapter;
import com.jiit.minor2.shubhamjoshi.human.Clustering.Kosaraju.Kosaraju;
import com.jiit.minor2.shubhamjoshi.human.MagicPosts;
import com.jiit.minor2.shubhamjoshi.human.Pagers.VerticalViewPager;
import com.jiit.minor2.shubhamjoshi.human.R;
import com.jiit.minor2.shubhamjoshi.human.Utils.Constants;
import com.jiit.minor2.shubhamjoshi.human.modals.Post;
import com.jiit.minor2.shubhamjoshi.human.modals.Twitter.TwitterData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static android.content.Context.MODE_PRIVATE;

public class NewsFeed extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private ArrayList<String> hint=new ArrayList<>();
    private String mParam2;
    VerticalViewPager verticalViewPager;
    ArrayList<String> datas = new ArrayList<String>();
    ConfigurationBuilder cb;
    List<TwitterData> mTwitterDatas = new ArrayList<>();
    Set<String> likes = new HashSet<>();
    String results = "";
    HashSet<String> set = new HashSet<>();
    private String email;
    private ArrayList<Post> wlist = new ArrayList<>();
    private ArrayList<Post> nlist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_new_feed, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        /*checking watsson */
        ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        service.setUsernameAndPassword("96035944-5998-4fad-a145-b44f21475b97", "vFjOzNGQvuBw");



        SharedPreferences prefs = getActivity().getSharedPreferences("EMAIL", MODE_PRIVATE);
        String email = prefs.getString("email", "NULL");


        if (email != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }


        //data of email got


        Firebase mref = new Firebase(Constants.BASE_URL);


        Log.e("SJ", email);

        mref.child("interests").child("joshihacked@yahoo,in").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap<String, String> m = (HashMap<String, String>) snapshot.getValue();
                likes = m.keySet();

                for (String s : likes) {
                    datas.add("#" + s.trim().toLowerCase());

                }

                for (int i = 0; i < datas.size() - 1; i++) {
                    results += datas.get(i) + " OR ";
                }

                results += datas.get(datas.size() - 1);

               // Log.e("results", results);
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

            /*watson */


            String text =
                    "I know the times are difficult! Our sales have been "
                            + "disappointing for the past three quarters for our data analytics "
                            + "product suite. We have a competitive data analytics product "
                            + "suite in the industry. But we need to do our job selling it! "
                            + "We need to acknowledge and fix our sales challenges. "
                            + "We can’t blame the economy for our lack of execution! "
                            + "We are missing critical sales opportunities. "
                            + "Our product is in no way inferior to the competitor products. "
                            + "Our clients are hungry for analytical tools to improve their "
                            + "business outcomes. Economy has nothing to do with it.";

// Call the service and get the tone
            ToneAnalysis tone = service.getTone(text, null).execute();
            System.out.println(tone);


            cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("t437OESAlLD5BWcbDJbkh7SC6")
                    .setOAuthConsumerSecret("YpKHZJritr8nhDYfuMWq7nq0Y8oFPmXja9Y9Zjz2n5X2JFfosS")
                    .setOAuthAccessToken("1547849809-I3uRGjJpRyppymN3bH0XAKRbfMpPTVIYw1i68zJ")
                    .setOAuthAccessTokenSecret("2ZsKxIvI6PhMQxdhaKOShnjVcEZaNtlIVuKCl7G3O9RIV");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

           // Log.e("SJ", datas.toString());


            try {
                Query query;
                if (results != "")
                    query = new Query(results);
                else
                    query = new Query("#srk");


                query.setCount(100);
                QueryResult result;
                result = twitter.search(query);
                List<twitter4j.Status> tweets = result.getTweets();

                Firebase f = new Firebase(Constants.BASE_URL).child("posts1");
                // f.removeValue();
                for (final twitter4j.Status tweet : tweets) {
                    MediaEntity[] media = tweet.getMediaEntities(); //get the media entities from the status
                    for (MediaEntity m : media) { //search trough your entities

                        Date date = tweet.getCreatedAt();
                        System.out.println("Today is " + date.getTime());

                        Firebase ff = new Firebase(Constants.BASE_URL);
                        final String[] rr = new String[1];
                        rr[0]="trends";
                        ff.child("interests").child("joshihacked@yahoo,in").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashtagEntity[] h = tweet.getHashtagEntities();
                                for (int i = 0; i < h.length; i++) {
                                    if (datas.contains("#" + h[i].getText().toLowerCase())) {
                                      // Log.e("Result is",h[i].getText().toLowerCase());
                                        hint.add(h[i].getText().toLowerCase());
                                        rr[0] = h[i].getText().toLowerCase();
                                    }
                                    rr[0]=h[i].getText().toLowerCase();
                                   // Log.e("CHECK", h[i].getText());
                                   // Log.e("DATA", datas.toString());
                                }

                                //Log.e("SJ", dataSnapshot.getValue() + " " + dataSnapshot.getKey());
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        TwitterData td = new TwitterData(m.getMediaURL(), tweet.getText(), date.getTime(),rr[0]);
                        mTwitterDatas.add(td);

                    }
                    for (int i = 0; i < mTwitterDatas.size(); i++) {
                        if (mTwitterDatas.get(i).getImageUrl() != null) {
                           // f.removeValue();

                            Post p = new Post(tweet.getUser().getScreenName(), mTwitterDatas.get(i).getImageUrl(), tweet.getUser().getProfileImageURL()
                                    , mTwitterDatas.get(i).getTag(), -1 * mTwitterDatas.get(i).getTimeStamp(), 0,mTwitterDatas.get(i).getData());

                           // Kosaraju k = new Kosaraju();
                            int []vis = new int[100];
                           // k.DFS(null,1,null,null);
                            if(!set.contains(p.getImageUrl())) {
                                f.push().setValue(p);
                                set.add(p.getImageUrl());
                            }
                        }
                    }

                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }

                f.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            Post post = child.getValue(Post.class);
                            wlist.add(post);
                           // Log.e("task", post.toString());
                        }


                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    verticalViewPager = (VerticalViewPager) getActivity().findViewById(R.id.verticleViewPager);

                                    if(wlist!=null)
                                        try {
                                            MagicPosts magicPosts = new MagicPosts(wlist,getResources());
                                            magicPosts.generateHashColors();
                                            nlist = magicPosts.getPost();
                                            //intk = nlist.size();



                                       //     Log.e("Originial ",nlist.size()+" "+magicPosts.getSize());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    if (wlist != null && verticalViewPager != null)
                                        verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity().getBaseContext(), nlist, hint));
                                }
                            });


                        }
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