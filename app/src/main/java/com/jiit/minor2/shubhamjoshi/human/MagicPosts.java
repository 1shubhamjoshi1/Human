package com.jiit.minor2.shubhamjoshi.human;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.jiit.minor2.shubhamjoshi.human.modals.Post;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.net.URL;
/**
 * Created by Shubham Joshi on 20-12-2016.
 */

public class MagicPosts {
    HashMap<String,Post> map = new HashMap<>();
    ArrayList<Post> list = new ArrayList<>();
    HashSet<String> set = new HashSet<>();
    int size=0;
    Resources mResources;
    public MagicPosts(ArrayList
                              <Post> list,Resources resources)
    {
        this.list = list;
        this.mResources = resources;

    }


    public void generateHashColors() throws IOException {

        new Work().execute();
    }
    public void process()
    {

        for(int i=0;i<list.size();i++)
            list.remove(i);

        for (String s : set) {
           list.add(map.get(s));
        }

      // Log.e("Size",list.size()+"");

    }

    public ArrayList<Post> getPost()
    {
        return list;
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    class Work extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            for(int ii=0;ii<list.size();ii++) {

                final int finalI = ii;
                final int finalIi = ii;
                URL url_value = null;
                try {
                    url_value = new URL(list.get(finalI).getImageUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap b = null;
                try {
                    b = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(b!=null) {
                    Palette.from(b).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette p) {
                            // Use generated instance
                            if (finalIi < list.size())
                                map.put(p.getMutedColor(Color.RED) + "" + p.getDarkVibrantColor(Color.RED)
                                        , list.get(finalIi));
                            set.add(p.getMutedColor(Color.RED) + "" + p.getDarkVibrantColor(Color.RED));
                           // Log.e("Color", "" + p.getMutedColor(Color.RED) + " " + list.size());
                            size = list.size();
                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            process();
        }
    }

    public int getSize() {
        return size;
    }
}
